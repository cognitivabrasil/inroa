--
-- PostgreSQL database dump
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: feb
--

--CREATE PROCEDURAL LANGUAGE plpgsql;
--ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO feb;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: plpgsql_call_handler(); Type: FUNCTION; Schema: public; Owner: feb
--

CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler
    AS '$libdir/plpgsql', 'plpgsql_call_handler'
    LANGUAGE c;

ALTER FUNCTION public.plpgsql_call_handler() OWNER TO feb;

--
-- Name: sim_acronyms(text, text); Type: FUNCTION; Schema: public; Owner: feb
--

CREATE FUNCTION sim_acronyms(word1 text, word2 text) RETURNS real
    AS $$
DECLARE
  similarity            real;
  token_index	        integer;
  token_data            text;
  separator             text;
  data1			text;
  data2			text;
  data_index		integer;
  acronym1		text;
  acronym2		text;
BEGIN
  similarity := 0;
  separator := ' ';

  -- normalize the words before the comparisons
  data1 := sim_acronyms_normalize(word1);
  data2 := sim_acronyms_normalize(word2);

  -- if there is not a separator in the data1 then it is an acronym.
  IF strpos(data1,separator) = 0 THEN
    acronym1 := data1;
  ELSE
    acronym1 := '';
    -- if there is at least one separator in the data1 then the acronym is formed by the upcase letters.
    FOR data_index IN 1..length(data1) LOOP
      IF strpos('ABCDEFGHIJKLMNOPQRSTUVWXYZ',substr(data1,data_index,1)) <> 0 THEN
        acronym1 := acronym1 || substr(data1,data_index,1);
      END IF;
    END LOOP;
  END IF;

  IF acronym1 = '' THEN
    token_index := 0;
    LOOP
      token_index := token_index + 1;
      token_data := split_part(data1,separator,token_index);
      EXIT WHEN token_data = '';
      acronym1 := acronym1 || substr(token_data,1,1);
    END LOOP;
  END IF;

  -- if there is not a separator in the data2 then it is an acronym.
  IF strpos(data2,separator) = 0 THEN
    acronym2 := data2;
  ELSE
    acronym2 := '';
    -- if there is at least one separator in the data2 then the acronym is formed by the upcase letters.
    FOR data_index IN 1..length(data2) LOOP
      IF strpos('ABCDEFGHIJKLMNOPQRSTUVWXYZ',substr(data2,data_index,1)) <> 0 THEN
        acronym2 := acronym2 || substr(data2,data_index,1);
      END IF;
    END LOOP;
  END IF;
  
  IF acronym2 = '' THEN
    token_index := 0;
    LOOP
      token_index := token_index + 1;
      token_data := split_part(data2,separator,token_index);
      EXIT WHEN token_data = '';
      acronym2 := acronym2 || substr(token_data,1,1);
    END LOOP;
  END IF;

  --PERFORM ins_debug(acronym1||'-'||acronym2);

  RETURN sim_guth(acronym1,acronym2);

END;
$$
    LANGUAGE plpgsql;


ALTER FUNCTION public.sim_acronyms(word1 text, word2 text) OWNER TO feb;

--
-- Name: sim_acronyms_normalize(text); Type: FUNCTION; Schema: public; Owner: feb
--

CREATE FUNCTION sim_acronyms_normalize(data text) RETURNS text
    AS $$

DECLARE
  wrong_alphabet    text;
  right_alphabet    text;
  new_data	    text;
  data_index	    integer;
  double_space_pos  integer;
  
BEGIN
  -- sets the wrong alphabet and the right alphabet
  wrong_alphabet := 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz¡¿ƒ¬√·‡‰‚„…»À ÈËÎÍÕÃœŒÌÏÔÓ”“÷‘’ÛÚˆÙı⁄Ÿ‹€˙˘¸˚ÁÒ0123456789';
  right_alphabet := 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzAAAAAaaaaaEEEEeeeeIIIIiiiiOOOOOoooooUUUUuuuucn          ';

  -- translate the data from the wrong alphabet to the right one
  new_data := translate(data,wrong_alphabet,right_alphabet);
  
  -- replaces the characters that do not belong to the right alphabet with spaces
  for data_index in 1..length(new_data) loop
    if strpos(right_alphabet,substr(new_data,data_index,1)) = 0 then
      new_data := substr(new_data,1,data_index-1)||' '||substr(new_data,data_index+1);
    end if;
  end loop;

  -- removes the double spaces
  double_space_pos := strpos(new_data,'  ');
  while double_space_pos <> 0 loop
    new_data := substr(new_data,1,double_space_pos-1)||substr(new_data,double_space_pos+1);
    double_space_pos := strpos(new_data,'  ');
  end loop;

  RETURN new_data;

END;
$$
    LANGUAGE plpgsql;
