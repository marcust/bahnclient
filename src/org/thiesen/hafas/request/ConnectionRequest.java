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
package org.thiesen.hafas.request;

import java.util.Collection;
import java.util.Collections;

import org.jdom.Element;
import org.thiesen.hafas.request.parts.LocationType;

import com.google.common.collect.ImmutableList;

public class ConnectionRequest extends BaseRequest {

    @Override
    protected Collection<Element> makeElement() {
        final Element element = new Element("ConReq");
        
        element.addContent( makeStart() );
        element.addContent( makeDest() );
        element.addContent( makeVia() );
        element.addContent( makeReqT() );
        element.addContent( makeRFlags() );
        element.addContent( makeGISParameters() );
        
        return Collections.singleton( element );
    }

    private Element makeGISParameters() {
        return new Element("GISParameters").addContent( ImmutableList.of( new Element("Front"), new Element("Back") ) );
        
    }

    private Element makeRFlags() {
        return new Element("RFlags").setAttribute("b", "0").setAttribute("f","6");
    }

    private Element makeReqT() {
        // a means is arrival time
        return new Element("ReqT").setAttribute( "time", "16:10" ).setAttribute( "date", "20111012" ).setAttribute( "a", "0" );
    }

    private Element makeVia() {
        return new Element("Via");
    }

    private Element makeDest() {
        return new Element("Dest").setContent( makeReqLoc("Bonn") );
    }

    private Element makeStart() {
        return new Element("Start").setContent( makeReqLoc("Hamburg Dammtor") );
    }
    
    private Element makeReqLoc( final String location ) {
        return new Element("ReqLoc").setAttribute("match", location).setAttribute( LocationType.ALLTYPE.makeAttribute() );
    }

    
}
