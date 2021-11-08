package com.uala.services.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.uala.services.model.Contact;
import com.uala.services.model.Response;
import com.uala.services.repository.Repository;

public class PutContactHandler implements RequestHandler<Contact, Response> {

    private Repository repository;

    public PutContactHandler() {
        this(new Repository());
    }

    public PutContactHandler(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Response handleRequest(Contact contact, Context context) {
        Response response = new Response();
        Contact contactAux = repository.load(Contact.class, contact.getId(), contact.getFirstName(),contact.getLastName());
            repository.updateContact(contact);
            response.setMessage("OK");
            response.setStatus("204");

        return response;
    }
}