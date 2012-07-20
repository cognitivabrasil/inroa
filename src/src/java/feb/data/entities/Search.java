package feb.data.entities;

import java.util.Date;

public class Search {
	private Integer id;
	private String text;
	private Date timestamp;
	
	public Search() {
		
	}

	public Search(String string, Date time) {
		text = string;
		timestamp = time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
