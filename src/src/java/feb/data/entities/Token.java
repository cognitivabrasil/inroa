package feb.data.entities;

public class Token {
	public static int TITLE = 1;
	public static int KEYWORD = 2;
	public static int DESCRIPTION = 3;
	
	private Integer id;
	private DocumentoReal documento;
	private String token;
	private Integer field;
	
        public Token(){
            
        }

	public Token(String t, DocumentoReal documentoReal, int i) {
		token = t;
		field = i;
		documento = documentoReal;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public DocumentoReal getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoReal documento) {
		this.documento = documento;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getField() {
		return field;
	}
	public void setField(Integer field) {
		this.field = field;
	}
	
	
}