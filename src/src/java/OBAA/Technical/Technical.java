/**
 * 
 */
package OBAA.Technical;

import java.util.List;

import java.util.Set;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


/**
 * @author Paulo Schreiner
 * 
 * Stub implementation, only implemented the location subfield.
 * 
 * TODO: Implement everything.
 *
 */
@Root(strict=false)
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Technical {
	@ElementList(required=false, inline=true)
	private List<Location> location;
        
        Set<String> format;
        String size;    
        Set <Requirement> requirement;
        String installationRemarks;
        String otherPlatformRequirements;
        Duration duration;
        Set <SupportedPlatform> supportedPlatforms;
        Set <PlatformSpecificFeature> platformSpecificFeature;
        Set <Service> service;


	/**
	 * @return the first location that starts with http://,
	 * null otherwise
	 */
	public String getLocation() {
		for(Location l : location) {
			if(l.getText().startsWith("http://")) {
				return l.getText();
			}
		}
		return null;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(List<Location> location) {
		this.location = location;
	}

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Set<String> getFormat() {
        return format;
    }

    public void setFormat(Set<String> format) {
        this.format = format;
    }

    public String getInstallationRemarks() {
        return installationRemarks;
    }

    public void setInstallationRemarks(String installationRemarks) {
        this.installationRemarks = installationRemarks;
    }

    public String getOtherPlatformRequirements() {
        return otherPlatformRequirements;
    }

    public void setOtherPlatformRequirements(String otherPlatformRequirements) {
        this.otherPlatformRequirements = otherPlatformRequirements;
    }

    public Set<PlatformSpecificFeature> getPlatformSpecificFeature() {
        return platformSpecificFeature;
    }

    public void setPlatformSpecificFeature(Set<PlatformSpecificFeature> platformSpecificFeature) {
        this.platformSpecificFeature = platformSpecificFeature;
    }

    public Set<Requirement> getRequirement() {
        return requirement;
    }

    public void setRequirement(Set<Requirement> requirement) {
        this.requirement = requirement;
    }

    public Set<Service> getService() {
        return service;
    }

    public void setService(Set<Service> service) {
        this.service = service;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Set<SupportedPlatform> getSupportedPlatforms() {
        return supportedPlatforms;
    }

    public void setSupportedPlatforms(Set<SupportedPlatform> supportedPlatforms) {
        this.supportedPlatforms = supportedPlatforms;
    }
	
	
}
