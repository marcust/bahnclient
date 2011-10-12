/*
 * (c) Copyright 2011 Marcus Thiesen (marcus@thiesen.org)
 *
 *  This file is part of Bahn Client.
 *
 *  Bahn Client is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Bahn Client is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with gitant.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.thiesen.hafas;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.thiesen.hafas.exception.ClientException;
import org.thiesen.hafas.exception.CommunicationException;
import org.thiesen.hafas.request.Request;
import org.thiesen.hafas.response.Response;

import com.google.common.base.Charsets;

public class Client {

    private final Configuration _config;
    private final DefaultHttpClient _httpClient;

    private Client( final Configuration config ) {
        _config = config;

        _httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());

    }

    public static Client create( final Configuration config ) {
        return new Client( config );
    }

    public Response doRequest( final Request request ) throws ClientException {
        final HttpPost postMethod = new HttpPost( _config.getBaseUrl() );

        try {
            final String requestString = request.makeStringRepresentation( _config );
            
            System.out.println( requestString );
            postMethod.setEntity( new StringEntity( requestString,  _config.getEncoding().name() ) );
        } catch ( final UnsupportedEncodingException e ) {
            throw new Error("Invalid java, UTF-8 not supported", e );
        }        

        try {
            final HttpResponse httpResponse = _httpClient.execute( postMethod );

            final StatusLine statusLine = httpResponse.getStatusLine();
            if ( statusLine.getStatusCode() != 200 ) {
                handleErrorResponse( httpResponse, statusLine );
            } else {
                final String extractedResponseContent = extractResponseContent( httpResponse );
                
                return request.makeResponseFrom( extractedResponseContent );
            }

        } catch ( final RuntimeException e ) {
            postMethod.abort();
            throw new ClientException( e );
        } catch ( final ClientProtocolException e ) {
            throw new ClientException( e );
        } catch ( final IOException e ) {
            throw new ClientException( e ); 
        }
        
        throw new RuntimeException("Should never arrive here!");

    }

    private static void handleErrorResponse( final HttpResponse httpResponse, final StatusLine statusLine ) throws CommunicationException, ClientException {
        try {
            final String contentString = extractResponseContent( httpResponse );

            throw new CommunicationException(  statusLine.getStatusCode(), contentString );

        } catch ( final IOException e ) {
            throw new ClientException( e );
        }
    }

    private static String extractResponseContent( final HttpResponse httpResponse ) throws IOException {
        final InputStream content = httpResponse.getEntity().getContent();

        final String contentString = IOUtils.toString( content );
        IOUtils.closeQuietly( content );
        return contentString;
    }

}
