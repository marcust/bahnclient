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
package org.thiesen.hafas.request.parts;

import org.jdom.Attribute;

public enum Version implements Attributable {
    v1_1( "1.1" ),
    v2_3( "2.3" );
    
    private final String _version;


    private Version( final String version ) {
        _version = version;
    }

    @Override
    public Attribute makeAttribute() {
         return new Attribute( "ver", _version );
    }

    public static Version getDefault() {
        return v1_1;
    }

}
