package se.mikael_langer.gwt.couch.client.domain;

public class DatabaseInformation {
	public String db_name;
	public int doc_count;
	public int doc_del_count;
	public int update_seq;
	public int purge_seq;
	public boolean compact_running;
	public int disk_size;
	public String instance_start_time;
	public int disk_format_version;
}
