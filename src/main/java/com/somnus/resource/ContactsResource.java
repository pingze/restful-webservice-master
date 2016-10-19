package com.somnus.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.somnus.domain.Contact;
import com.somnus.support.ContactStore;
import com.sun.jersey.api.NotFoundException;

@Path("/contacts")
public class ContactsResource {
	
	@GET
	@Path("getContacts")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Contact> getContacts() {
		List<Contact> contacts = new ArrayList<Contact>();
		contacts.addAll( ContactStore.getStore().values() );
		return contacts;
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = ContactStore.getStore().size();
		return String.valueOf(count);
	}
	
	@GET
	@Path("{contact}")
	@Produces({MediaType.APPLICATION_JSON})
	public Contact getContact(
			@PathParam("contact") String id) {
	    Contact contact = ContactStore.getStore().get(id);
        if(contact==null)
            throw new NotFoundException("No such Contact.");
        return contact;
	}
	
	@POST
    @Path("addContact")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public List<Contact> addContact(Contact contact) {
	    List<Contact> contacts = new ArrayList<Contact>();
        contacts.addAll( ContactStore.getStore().values() );
        contacts.add(contact);
        return contacts;
    }
	
	/**
	 * 通过用户的请求获取用户的ip地址和主机信息
	 * @param request 用户发送的请求
	 * @return String 返回类型  （地址跳转参数）
	 * @throws Exception
	 */
	@GET
	@Path("getUserIp")
	@Produces({MediaType.APPLICATION_JSON})
	public String getRequestUserIp(@Context HttpServletRequest request) {
		
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
			ipAddress = request.getHeader("Proxy-Client-IP");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
			ipAddress = request.getHeader("HTTP_CLIENT_IP");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
			ipAddress = request.getRemoteAddr();
		return ipAddress;
	}
}
