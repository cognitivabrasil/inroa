--
-- Name: sim_guth_normalize(text); Type: FUNCTION; Schema: public; Owner: feb
--

CREATE FUNCTION sim_guth_normalize(data text) RETURNS text
    AS $_$

DECLARE
  wrong_alphabet    text;
  right_alphabet    text;
  new_data	    text;
  data_index	    integer;
  double_space_pos  integer;
  
BEGIN
  -- sets the wrong alphabet and the right alphabet
  wrong_alphabet := ';ABCDEFGHIJKLMNOPQRSTUVWXYZ¡¿ƒ¬√·‡‰‚„…»À ÈËÎÍÕÃœŒÌÏÔÓ”“÷‘’ÛÚˆÙı⁄Ÿ‹€˙˘¸˚ÁÒ0123456789';
  right_alphabet := '$abcdefghijklmnopqrstuvwxyzaaaaaaaaaaeeeeeeeeiiiiiiiioooooooooouuuuuuuucn          ';

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
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.sim_guth_normalize(data text) OWNER TO feb;


--
-- Name: sim_guth(text, text); Type: FUNCTION; Schema: public; Owner: feb
--

CREATE FUNCTION sim_guth(word1 text, word2 text) RETURNS real
    AS $$
DECLARE
  similarity            real;
  data1			text;
  data2			text;
  data1_flags		text;
  data2_flags		text;
  data_index		integer;
  data_aux		text;
  
BEGIN
  similarity := 0;

  -- normalize the words before the comparisons
  data1 := sim_guth_normalize(word1);
  data2 := sim_guth_normalize(word2);

  -- the main string (data1) has to be the bigger one
  IF length(data1) < length(data2) THEN
    data_aux := data1;
    data1    := data2;
    data2    := data_aux;
  END IF;

  data1_flags := data1;
  data2_flags := data2;

  similarity := 0;
  -- executes the matching tests
  FOR data_index IN 1 .. length(data1) LOOP
    IF sim_guth_compare(data1, data_index, data1_flags, data2, data_index, data2_flags) THEN -- X = X
       data1_flags := sim_guth_flags(data1_flags,data_index);
       data2_flags := sim_guth_flags(data2_flags,data_index);
       --PERFORM ins_debug(data1_flags||'-'||data2_flags);
       similarity := similarity + 1;
    ELSEIF sim_guth_compare(data1, data_index, data1_flags, data2, data_index + 1, data2_flags) THEN -- X = X + 1
       data1_flags := sim_guth_flags(data1_flags,data_index);
       data2_flags := sim_guth_flags(data2_flags,data_index + 1);
       --PERFORM ins_debug(data1_flags||'-'||data2_flags);
       similarity := similarity + 1;
    ELSEIF sim_guth_compare(data1, data_index, data1_flags, data2, data_index + 2, data2_flags) THEN -- X = X + 2
       data1_flags := sim_guth_flags(data1_flags,data_index);
       data2_flags := sim_guth_flags(data2_flags,data_index + 2);
       --PERFORM ins_debug(data1_flags||'-'||data2_flags);
       similarity := similarity + 1;
    ELSEIF sim_guth_compare(data1, data_index, data1_flags, data2, data_index - 1, data2_flags) THEN -- X = X - 1
       data1_flags := sim_guth_flags(data1_flags,data_index);
       data2_flags := sim_guth_flags(data2_flags,data_index - 1);
       --PERFORM ins_debug(data1_flags||'-'||data2_flags);
       similarity := similarity + 1;
    ELSEIF sim_guth_compare(data1, data_index - 1, data1_flags, data2, data_index, data2_flags) THEN -- X - 1 = X
       data1_flags := sim_guth_flags(data1_flags,data_index - 1);
       data2_flags := sim_guth_flags(data2_flags,data_index);
       --PERFORM ins_debug(data1_flags||'-'||data2_flags);
       similarity := similarity + 1;
    ELSEIF sim_guth_compare(data1, data_index + 1, data1_flags, data2, data_index, data2_flags) THEN -- X + 1 = X
       data1_flags := sim_guth_flags(data1_flags,data_index + 1);
       data2_flags := sim_guth_flags(data2_flags,data_index);
       --PERFORM ins_debug(data1_flags||'-'||data2_flags);
       similarity := similarity + 1;
    ELSEIF sim_guth_compare(data1, data_index + 2, data1_flags, data2, data_index, data2_flags) THEN -- X + 2 = X
       data1_flags := sim_guth_flags(data1_flags,data_index + 2);
       data2_flags := sim_guth_flags(data2_flags,data_index);
       --PERFORM ins_debug(data1_flags||'-'||data2_flags);
       similarity := similarity + 1;
    ELSEIF sim_guth_compare(data1, data_index + 1, data1_flags, data2, data_index + 1, data2_flags) THEN -- X + 1 = X + 1
       data1_flags := sim_guth_flags(data1_flags,data_index + 1);
       data2_flags := sim_guth_flags(data2_flags,data_index + 1);
       --PERFORM ins_debug(data1_flags||'-'||data2_flags);
       similarity := similarity + 1;
    ELSEIF sim_guth_compare(data1, data_index + 2, data1_flags, data2, data_index + 2, data2_flags) THEN -- X + 2 = X + 2
       data1_flags := sim_guth_flags(data1_flags,data_index + 2);
       data2_flags := sim_guth_flags(data2_flags,data_index + 2);
       --PERFORM ins_debug(data1_flags||'-'||data2_flags);
       similarity := similarity + 1;
    ELSE 
       similarity := 0;
       RETURN similarity;
    END IF;
  END LOOP;

  RETURN similarity/length(data1);

END;
$$
    LANGUAGE plpgsql;


ALTER FUNCTION public.sim_guth(word1 text, word2 text) OWNER TO feb;

--
-- Name: sim_guth_compare(text, integer, text, text, integer, text); Type: FUNCTION; Schema: public; Owner: feb
--

CREATE FUNCTION sim_guth_compare(data_a text, data_a_index integer, data_a_flags text, data_b text, data_b_index integer, data_b_flags text) RETURNS boolean
    AS $$

BEGIN
  IF substr(data_a_flags,data_a_index,1) <> '1' AND 
     -- this algorithm allow letters from A to be present only once in the neighborhood of that letter in B
     -- substr(data_b_flags,data_b_index,1) <> '1' AND
     data_a_index <= length(data_a) AND
     data_b_index <= length(data_b) AND
     substr(data_a,data_a_index,1) = substr(data_b,data_b_index,1) THEN
    RETURN true;
  ELSE
    RETURN false;
  END IF;

END;
$$
    LANGUAGE plpgsql;


ALTER FUNCTION public.sim_guth_compare(data_a text, data_a_index integer, data_a_flags text, data_b text, data_b_index integer, data_b_flags text) OWNER TO feb;

--
-- Name: sim_guth_flags(text, integer); Type: FUNCTION; Schema: public; Owner: feb
--

CREATE FUNCTION sim_guth_flags(data_flags text, data_index integer) RETURNS text
    AS $$

BEGIN
  
  RETURN substr(data_flags,1,data_index-1)||'1'||substr(data_flags,data_index+1);

END;
$$
    LANGUAGE plpgsql;


ALTER FUNCTION public.sim_guth_flags(data_flags text, data_index integer) OWNER TO feb;

