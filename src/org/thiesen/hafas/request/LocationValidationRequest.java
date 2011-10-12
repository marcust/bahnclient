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

import org.jdom.Element;
import org.thiesen.hafas.request.parts.LocationType;

import com.google.common.collect.ImmutableList;

public class LocationValidationRequest extends BaseRequest {

    private static class ReqLocation {
        private final String _name;
        private final LocationType _type;

        private ReqLocation( final String name, final LocationType type ) {
            _name = name;
            _type = type;
        }

        public Element makeElement() {
            final Element element = new Element("ReqLoc");
            
            element.setAttribute("match", _name );
            element.setAttribute( _type.makeAttribute() );
            
            
            
            return element;
        }
    }
    
    private final ReqLocation _from;
    private final ReqLocation _to;
    
    private LocationValidationRequest( final ReqLocation from, final ReqLocation to ) {
        _from = from;
        _to = to;
    }

    public static LocationValidationRequest fromTo( final String from, final LocationType fromType, final String to, final LocationType toType ) {
        return new LocationValidationRequest( new ReqLocation( from, fromType), new ReqLocation( to, toType ) );
    }

    @Override
    protected Collection<Element> makeElement() {
        return ImmutableList.of( makeElementFor( "1", _from),
                makeElementFor( "2", _to ) );
    }

    private Element makeElementFor( final String type, final ReqLocation loc ) {
        final Element element = new Element("LocValReq");
        element.setAttribute( "id", type );
        element.setAttribute("maxNr", "1");
        element.setAttribute("sMode", "5");
        
        element.addContent( loc.makeElement() );

        return element;
    }



}
