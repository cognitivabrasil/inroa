package feb.spring.dtos;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import metadata.XsltConversor;
import cognitivabrasil.obaa.OaiException;
import cognitivabrasil.obaa.OaiOBAA;
import feb.data.entities.Mapeamento;
import feb.metadata.XSLTUtil;

public class MapeamentoDto {
	private Integer id;
	private String xml;
	private String xmlObaa;
	private String xslt;
	private String name;
	private String description;
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
		if (!(xml == null || xml.isEmpty())) {
			try {
				XsltConversor conv = new XsltConversor(xslt);
				xmlObaa = XSLTUtil.formatXml(conv.toObaa(xml));
				OaiOBAA.fromString(xmlObaa);
			} catch (RuntimeException e) {
				xmlObaa = e.getMessage() + "\n" + getStackTrace(e);
			}
		} else {
			xmlObaa = "Nenhum XML original informado";
		}
	}
}
