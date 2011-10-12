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
package org.thiesen.bahn;

import org.thiesen.hafas.Client;
import org.thiesen.hafas.exception.ClientException;
import org.thiesen.hafas.request.ConnectionRequest;
import org.thiesen.hafas.request.LocationValidationRequest;
import org.thiesen.hafas.request.parts.LocationType;
import org.thiesen.hafas.response.Response;

public class Test {

    public static void main( final String[] args ) throws ClientException {
        final Client client = Client.create( BahnConfiguration.instance() );
        
        final LocationValidationRequest validationRequest = LocationValidationRequest.fromTo( "Bonn Hbf", LocationType.ALLTYPE, "Hamburg Dammtor", LocationType.ALLTYPE );
        
        final Response validationResponse = client.doRequest( validationRequest );
        
        System.out.println("Validation Response:\n" + validationResponse);
        
        final Response response = client.doRequest( new ConnectionRequest() );
        
        System.out.println(response);
        
        
    }

}
