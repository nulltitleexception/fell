package OLDcom.monolc.fell.net;

import OLDcom.monolc.fell.version.VersionData;

public class ClientStatus {
	boolean recievedHeader;
	boolean needsMap;
	String version;
	VersionData serverVersion;
	public ClientStatus() {
		serverVersion = null;
		recievedHeader = false;
		needsMap = true;
		version = "";
	}
	public void setHasMap(boolean b){
		needsMap = !b;
	}
	public void setInitialState(String header) {
		version = header;
	}
	public boolean hasRecievedInitialData() {
		return recievedHeader;
	}
	public boolean hasMap() {
		return !needsMap;
	}
	public boolean isValid() {
		return getValidityMessage() == null;
	}
	public String getValidityMessage() {
		if (!version.equals(serverVersion.getVersion())) {
			return "INCORRECT VERSION: \"" + version + "\" != \"" + serverVersion.getVersion() + "\"";
		}
		return null;
	}
	public void informOfVersion(VersionData v) {
		serverVersion = v;
	}
}
