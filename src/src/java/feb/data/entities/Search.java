package feb.data.entities;

import java.util.Date;

public class Search {
	private String text;
	private Integer times;
	
	public Search() {
		
	}

	public Search(String string, Integer times) {
		text = string;
		this.setCount(times);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getCount() {
		return times;
	}

	public void setCount(Integer times) {
		this.times = times;
	}
	
}
