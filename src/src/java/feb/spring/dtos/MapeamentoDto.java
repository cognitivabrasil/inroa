package feb.spring.dtos;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import metadata.XsltConversor;
import cognitivabrasil.obaa.OaiOBAA;
import feb.data.entities.Mapeamento;
import feb.data.interfaces.PadraoMetadadosDAO;
import feb.metadata.XSLTUtil;

public class MapeamentoDto {
	private Integer id;
	private String xml;
	private String xmlObaa;
	private String xslt;
	private String name;
	private String description;
	private String preview;
	private String submit;
	private boolean failed;

	private Integer padraoMetadados;

	public MapeamentoDto() {
	}

	public MapeamentoDto(Mapeamento m) {
		xslt = m.getXslt();
		id = m.getId();
		name = m.getName();
		description = m.getDescription();
		if (m.getPadraoMetadados() != null) {
			padraoMetadados = m.getPadraoMetadados().getId();
		}
	}

	/**
	 * 
	 * @return true if this object should be created, false, otherwise
	 */
	public boolean create() {
		if (submit == null || submit.isEmpty() || failed) {
			return false;
		} else {
			return true;
		}
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getXmlObaa() {
		return xmlObaa;
	}

	public void setXmlObaa(String xmlObaa) {
		this.xmlObaa = xmlObaa;
	}

	public String getXslt() {
		return xslt;
	}

	public void setXslt(String xslt) {
		this.xslt = xslt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPadraoMetadados() {
		return padraoMetadados;
	}

	public void setPadraoMetadados(Integer padraoMetadados) {
		this.padraoMetadados = padraoMetadados;
	}

	private static String getStackTrace(Throwable throwable) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		throwable.printStackTrace(printWriter);
		return writer.toString();
	}

	public void transform() {
		try {
			XsltConversor conv = new XsltConversor(xslt);
			if (!(xml == null || xml.isEmpty())) {

				xmlObaa = XSLTUtil.formatXml(conv.toObaa(xml));
				OaiOBAA.fromString(xmlObaa);

			} else {
				xmlObaa = "Nenhum XML original informado";
			}
		} catch (RuntimeException e) {
			xmlObaa = e.getMessage() + "\n" + getStackTrace(e);
			failed = true;
		}

	}

	public Mapeamento createMapeamento(PadraoMetadadosDAO padraoDao) {
		Mapeamento m = new Mapeamento();
		m.setId(id);
		m.setPadraoMetadados(padraoDao.get(padraoMetadados));
		m.setName(name);
		m.setDescription(description);
		m.setXslt(xslt);

		return m;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}
}
