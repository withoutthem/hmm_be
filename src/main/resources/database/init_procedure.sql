-- API 호출 Procedure
CREATE OR REPLACE PROCEDURE CBUI_WAS.call_noti_api IS
  req   UTL_HTTP.req;
  resp  UTL_HTTP.resp;
  url   VARCHAR2(4000) := 'http://host.docker.internal:18080/api/notice/birthDay';
  buffer VARCHAR2(32767);
BEGIN
  -- 1. 테이블 데이터 → JSON 배열로 변환
FOR rec IN (SELECT user_id, user_name, TO_CHAR(birth_day, 'YYYY-MM-DD') AS birth_day FROM birthday_notice) LOOP
    json_obj := JSON_OBJECT_T();
    json_obj.put('userId', rec.user_id);
    json_obj.put('userName', rec.user_name);
    json_obj.put('birthDay', rec.birth_day);
    json_arr.append(json_obj);
END LOOP;

  -- 2. HTTP 요청 생성
  req := UTL_HTTP.begin_request(url, 'POST', 'HTTP/1.1');
  UTL_HTTP.set_header(req, 'Content-Type', 'application/json');
  UTL_HTTP.set_header(req, 'Content-Length', LENGTH(json_arr.to_string));
  UTL_HTTP.write_text(req, json_arr.to_string);

  LOOP
BEGIN
      UTL_HTTP.read_line(resp, buffer, TRUE);
      DBMS_OUTPUT.put_line(buffer);
EXCEPTION
      WHEN NO_DATA_FOUND THEN
        EXIT; -- 본문 끝에 도달했으므로 루프 종료
END;
END LOOP;

  UTL_HTTP.end_response(resp);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.put_line('Error: ' || SQLERRM);
  	DBMS_OUTPUT.put_line(UTL_HTTP.get_detailed_sqlerrm);
END;

-- 시퀀스 생성 공통용
CREATE SEQUENCE SEQ_GLOBAL_ID
    START WITH 1
    INCREMENT BY 1
    NOCACHE
  NOCYCLE;


-- 유니크 ID 생성 Function
CREATE OR REPLACE FUNCTION FN_GENERATE_UNIQUE_ID
RETURN VARCHAR2
IS
  v_seq NUMBER;
  v_ts  VARCHAR2(14);
  v_id  VARCHAR2(30);
BEGIN
  -- 시퀀스 값 가져오기
SELECT SEQ_GLOBAL_ID.NEXTVAL INTO v_seq FROM DUAL;

-- 타임스탬프 생성 (초 단위까지)
SELECT TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISS') INTO v_ts FROM DUAL;

-- 고유 ID 조합: yyyyMMddHHmmss_seq
v_id := v_ts || '_' || LPAD(v_seq, 6, '0');

RETURN v_id;
END;
/

---

