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

import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;
import org.junit.Test;

/**
 *
 * @author rainer prosi
 * @date Jan 5, 2011
 */
public class RFQTest extends PrintTalkTestCase
{
	/**
	 *
	 *
	 */
	@Test
	public void testgetPrintTalk()
	{
		final PrintTalk printTalk = new PrintTalk();
		final RFQ po = (RFQ) printTalk.appendRequest(EnumBusinessObject.RFQ, null);
		assertEquals(printTalk, po.getPrintTalk());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testsetXJDF()
	{
		final PrintTalk printTalk = new PrintTalk();
		final PurchaseOrder po = (PurchaseOrder) printTalk.appendRequest(EnumBusinessObject.PurchaseOrder, null);
		final XJDFHelper h = new XJDFHelper("j1", null, null);
		po.setXJDF(h);
		assertEquals("j1", po.getXJDF(0).getJobID());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testsetExpiresDays()
	{
		final PrintTalk printTalk = new PrintTalk();
		final RFQ rfq = (RFQ) printTalk.appendRequest(EnumBusinessObject.RFQ, null);
		rfq.setExpiresDays(5);
		final JDFDate expires = rfq.getExpires();
		assertEquals(5 * 1000l * 3600l * 24l, expires.getTimeInMillis() - System.currentTimeMillis(), 1000l * 3600l * 24);
		assertEquals(JDFDate.getDefaultHour(), expires.getHour());
		assertEquals(0, expires.getMinute());
		assertEquals(0, expires.getSecond());
		rfq.cleanUp();
		reparse(rfq, false);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testCurrency()
	{
		final PrintTalk printTalk = new PrintTalk();
		final RFQ rfq = (RFQ) printTalk.appendRequest(EnumBusinessObject.RFQ, null);
		rfq.setExpiresDays(5);
		rfq.setCurrency("eur");
		rfq.cleanUp();
		reparse(rfq, false);
	}

	/**
	*
	*
	*/
	@Test
	public void testCurrencyBad()
	{
		final PrintTalk printTalk = new PrintTalk();
		final RFQ rfq = (RFQ) printTalk.appendRequest(EnumBusinessObject.RFQ, null);
		rfq.setExpiresDays(5);
		rfq.setCurrency("€");
		rfq.cleanUp();
		reparse(rfq, true);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testappendXJDF()
	{
		final PrintTalk printTalk = new PrintTalk();
		final RFQ po = (RFQ) printTalk.appendRequest(EnumBusinessObject.RFQ, null);
		final XJDFHelper h = new XJDFHelper("j1", null, null);
		po.appendXJDF(h);
		final XJDFHelper h2 = new XJDFHelper("j2", null, null);
		po.appendXJDF(h2);
		assertEquals("j1", printTalk.getXPathAttribute("Request/RFQ/XJDF/@JobID", null));
		assertEquals("j2", printTalk.getXPathAttribute("Request/RFQ/XJDF[2]/@JobID", null));
		po.cleanUp();
		po.setExpiresDays(4);
		reparse(po, false);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetXJDFs()
	{
		final PrintTalk printTalk = new PrintTalk();
		final RFQ po = (RFQ) printTalk.appendRequest(EnumBusinessObject.RFQ, null);
		final XJDFHelper h = new XJDFHelper("j1", null, null);
		po.appendXJDF(h);
		final XJDFHelper h2 = new XJDFHelper("j2", null, null);
		po.appendXJDF(h2);
		assertEquals("j1", po.getXJDFs().get(0).getJobID());
		assertEquals("j2", po.getXJDFs().get(1).getJobID());
	}
}
