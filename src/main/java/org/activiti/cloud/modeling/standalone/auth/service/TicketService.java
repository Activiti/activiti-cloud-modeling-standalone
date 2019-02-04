package org.activiti.cloud.modeling.standalone.auth.service;

import java.util.concurrent.ConcurrentHashMap;

import org.activiti.cloud.modeling.standalone.auth.rest.model.Entry;

public class TicketService {

	private static final ConcurrentHashMap<String, String> ticketMap = new ConcurrentHashMap<String, String>();

	public static void setTicket(Entry entry) {
		ticketMap.put(entry.getId(), entry.getUserId());
	}

	public static String getUserId(String id) {
		return ticketMap.get(id);
	}

	public static void deleteTicket(String id) {
		if (ticketMap.containsKey(id)) {
			ticketMap.remove(id);
		}
	}
}
