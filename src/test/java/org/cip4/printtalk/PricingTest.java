/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2018 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This product includes software developed by the The International Cooperation for
 * the Integration of Processes in Prepress, Press and Postpress (www.cip4.org)" Alternately, this acknowledgment may appear in the software itself, if and wherever such third-party acknowledgments
 * normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of Processes in Prepress, Press and Postpress" must not be used to endorse or promote products derived from this software
 * without prior written permission. For written permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4", nor may "CIP4" appear in their name, without prior written permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE. ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the The International Cooperation for the Integration of Processes in Prepress, Press and Postpress and was
 * originally based on software copyright (c) 1999-2001, Heidelberger Druckmaschinen AG copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the Integration of Processes in Prepress, Press and Postpress , please see <http://www.cip4.org/>.
 *
 *
 */
package org.cip4.printtalk;

import org.cip4.jdflib.core.XMLDoc;
import org.cip4.printtalk.Price.EnumPriceType;
import org.cip4.printtalk.Price.EnumTaxType;
import org.junit.Test;

/**
 *
 * @author rainer prosi
 * @date Jan 4, 2011
 */
public class PricingTest extends PrintTalkTestCase
{
	/**
	 *
	 *
	 */
	@Test
	public void testPriceByType()
	{
		final Pricing p = new Pricing(new XMLDoc(Pricing.ELEMENT_PRICING, null).getRoot());
		final Price p1 = p.addPrice("p1", 20);
		p1.setTaxType(EnumTaxType.Net);
		p1.setPriceType(EnumPriceType.Handling);

		final Price p2 = p.addPrice("p2", 20);
		p2.setTaxType(EnumTaxType.Tax);
		p2.setPriceType(EnumPriceType.Handling);

		assertEquals(p1, p.getPriceByType(EnumPriceType.Handling, EnumTaxType.Net, null, 0));
		assertEquals(p1, p.getPriceByType(EnumPriceType.Handling, null, null, 0));
		assertEquals(p2, p.getPriceByType(EnumPriceType.Handling, null, null, -1));
		assertEquals(p2, p.getPriceByType(null, null, null, -1));
		assertNull(p.getPriceByType(EnumPriceType.Handling, EnumTaxType.Gross, null, 0));
		assertNull(p.getPriceByType(EnumPriceType.Other, null, null, 0));

	}

	/**
	 *
	 *
	 */
	@Test
	public void testPricesByType()
	{
		final Pricing p = new Pricing(new XMLDoc(Pricing.ELEMENT_PRICING, null).getRoot());
		final Price p1 = p.addPrice("p1", 20);
		p1.setTaxType(EnumTaxType.Net);
		p1.setPriceType(EnumPriceType.Handling);

		final Price p2 = p.addPrice("p2", 20);
		p2.setTaxType(EnumTaxType.Tax);
		p2.setPriceType(EnumPriceType.Handling);

		assertNull(p.getPricesByType(EnumPriceType.Handling, EnumTaxType.Gross, null));
		assertTrue(p.getPricesByType(EnumPriceType.Handling, null, null).contains(p1));
		assertTrue(p.getPricesByType(EnumPriceType.Handling, null, null).contains(p2));

	}

	/**
	 *
	 *
	 */
	@Test
	public void testPriceByTypeDrop()
	{
		final Pricing p = new Pricing(new XMLDoc(Pricing.ELEMENT_PRICING, null).getRoot());
		final Price p1 = p.addPrice("p1", 20);
		p1.setTaxType(EnumTaxType.Net);
		p1.setPriceType(EnumPriceType.Handling);
		p1.setDropID("d1");

		final Price p2 = p.addPrice("p2", 20);
		p2.setTaxType(EnumTaxType.Tax);
		p2.setPriceType(EnumPriceType.Handling);
		p2.setDropID("d1");

		final Price p21 = p.addPrice("p1", 20);
		p21.setTaxType(EnumTaxType.Net);
		p21.setPriceType(EnumPriceType.Handling);
		p21.setDropID("d2");

		final Price p22 = p.addPrice("p2", 20);
		p22.setTaxType(EnumTaxType.Tax);
		p22.setPriceType(EnumPriceType.Handling);
		p22.setDropID("d2");

		assertEquals(p1, p.getPriceByType(EnumPriceType.Handling, EnumTaxType.Net, "d1", 0));
		assertEquals(p21, p.getPriceByType(EnumPriceType.Handling, EnumTaxType.Net, "d2", 0));
		assertEquals(p1, p.getPriceByType(EnumPriceType.Handling, null, "d1", 0));
		assertEquals(p21, p.getPriceByType(EnumPriceType.Handling, null, "d2", 0));
		assertNull(p.getPriceByType(EnumPriceType.Handling, null, "d3", 0));

	}

	/**
	 *
	 *
	 */
	@Test
	public void testPricesByTypeDrop()
	{
		final Pricing p = new Pricing(new XMLDoc(Pricing.ELEMENT_PRICING, null).getRoot());
		final Price p1 = p.addPrice("p1", 20);
		p1.setTaxType(EnumTaxType.Net);
		p1.setPriceType(EnumPriceType.Handling);
		p1.setDropID("d1");

		final Price p2 = p.addPrice("p2", 20);
		p2.setTaxType(EnumTaxType.Tax);
		p2.setPriceType(EnumPriceType.Handling);
		p2.setDropID("d1");

		final Price p21 = p.addPrice("p1", 20);
		p21.setTaxType(EnumTaxType.Net);
		p21.setPriceType(EnumPriceType.Handling);
		p21.setDropID("d2");

		final Price p22 = p.addPrice("p2", 20);
		p22.setTaxType(EnumTaxType.Tax);
		p22.setPriceType(EnumPriceType.Handling);
		p22.setDropID("d2");

		assertEquals(p22, p.getPricesByType(EnumPriceType.Handling, EnumTaxType.Tax, "d2").get(0));
	}
}
