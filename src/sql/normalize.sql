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
  wrong_alphabet := 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzÁÀÄÂÃáàäâãÉÈËÊéèëêÍÌÏÎíìïîÓÒÖÔÕóòöôõÚÙÜÛúùüûçñ0123456789';
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
