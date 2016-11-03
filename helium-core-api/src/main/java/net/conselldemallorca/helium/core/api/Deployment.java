package net.conselldemallorca.helium.core.api;

public interface Deployment {

	public String getId();
	public String getKey();
	public int getVersion();
	public Object getProcessDefinition();
	
}
