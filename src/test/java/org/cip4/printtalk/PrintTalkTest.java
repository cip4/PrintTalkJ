/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2023 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.elementwalker.RemovePrivate;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.printtalk.HeaderBase.EnumHeaderType;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;
import org.junit.Test;

/**
 *
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public class PrintTalkTest extends PrintTalkTestCase
{
	/**
	 *
	 * duh...
	 */
	@Test
	public void testSetHeader()
	{
		final PrintTalk pt = new PrintTalk();
		pt.setCredential(EnumHeaderType.From, "ID", "Its me");
		assertEquals(pt.getXPathAttribute("Header/From/Credential/Identity", null), "Its me");
		assertEquals(pt.getCredentialIdentity(EnumHeaderType.From, "ID"), "Its me");
		pt.setCredential(EnumHeaderType.From, "ID", "Its you");
		assertEquals(pt.getXPathAttribute("Header/From/Credential/Identity", null), "Its you");
	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testICSVersions()
	{
		final PrintTalk pt = new PrintTalk();
		assertNull(pt.getICSVersions());
		pt.setICSVersions(new VString("Cus-EP_L1-2.0"));
		assertEquals(new VString("Cus-EP_L1-2.0"), pt.getICSVersions());
		final BusinessObject req = pt.appendRequest(EnumBusinessObject.Cancellation, null);
		reparse(req, 20, false);
	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testIsPTKNS()
	{
		final PrintTalk pt = new PrintTalk();
		assertTrue(PrintTalk.isInPTKNameSpace(pt.getRoot().getNamespaceURI()));
	}

	/**
	 * 
	 */
	@Test
	public void testRemovePrivate()
	{
		final PrintTalk pt = new PrintTalk();
		pt.getRoot().setAttribute("foo:bar", "abc", "www.foo.com");
		final RemovePrivate rp = new RemovePrivate();
		rp.walkTree(pt.getRoot(), null);
		assertNull(pt.getAttribute("foo:bar"));
	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testGetNSUri()
	{
		assertEquals(PrintTalk.getNamespaceURI(20), "http://www.printtalk.org/schema_20");
	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testAppendRequest()
	{
		final PrintTalk pt = new PrintTalk();
		pt.setCredential(EnumHeaderType.From, "ID", "Its me");
		assertEquals(pt.getXPathAttribute("Header/From/Credential/Identity", null), "Its me");
		final BusinessObject bo = pt.appendRequest(EnumBusinessObject.RFQ, null);
		assertTrue(bo instanceof RFQ);
	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testAppendResponse()
	{
		final PrintTalk pt = new PrintTalk();
		final BusinessObject bo = pt.appendResponse(EnumBusinessObject.RFQ, null);
		assertTrue(bo instanceof RFQ);
	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testAppendRequestResponse()
	{
		final PrintTalk pt = new PrintTalk();
		final BusinessObject bo = pt.appendResponse(EnumBusinessObject.RFQ, null);
		assertTrue(bo instanceof RFQ);
		try
		{
			pt.appendResponse(EnumBusinessObject.RFQ, null);
			fail("no two");
		}
		catch (final IllegalArgumentException x)
		{
			//
		}
		try
		{
			pt.appendRequest(EnumBusinessObject.RFQ, null);
			fail("no two");
		}
		catch (final IllegalArgumentException x)
		{
			//
		}
	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testAppendRequestRef()
	{
		final PrintTalk pt = new PrintTalk();
		final BusinessObject bo = pt.appendRequest(EnumBusinessObject.RFQ, null);
		assertTrue(bo instanceof RFQ);
		final PrintTalk pt2 = new PrintTalk();
		final BusinessObject bo2 = pt2.appendRequest(EnumBusinessObject.Quotation, pt);
		assertTrue(bo2 instanceof Quotation);
		assertEquals(bo2.getBusinessRefID(), bo.getBusinessID());
	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testGetTimestamp()
	{
		final PrintTalk pt = new PrintTalk();
		assertEquals(new JDFDate().getTimeInMillis() - pt.getTimestamp().getTimeInMillis(), 6666, 6666);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testVersion()
	{
		final PrintTalk pt = new PrintTalk();
		assertEquals(pt.getNamespaceURI(), "http://www.printtalk.org/schema_20");
		pt.setVersion(13);
		assertEquals(pt.getNamespaceURI(), "http://www.printtalk.org/schema_13");
		pt.setVersion(15);
		assertEquals(pt.getNamespaceURI(), "http://www.printtalk.org/schema_15");
		pt.setVersion(20);
		assertEquals(pt.getNamespaceURI(), "http://www.printtalk.org/schema_20");
		pt.setVersion(22);
		assertEquals(pt.getNamespaceURI(), "http://www.printtalk.org/schema_20");
	}

	/**
	 *
	 *
	 */
	@Test
	public void testdefaultVersion()
	{
		PrintTalk.setDefaultVersion(20);
		assertEquals(new PrintTalk().getNamespaceURI(), "http://www.printtalk.org/schema_20");
		PrintTalk.setDefaultVersion(13);
		assertEquals(new PrintTalk().getNamespaceURI(), "http://www.printtalk.org/schema_13");
		PrintTalk.setDefaultVersion(22);
		assertEquals(new PrintTalk().getNamespaceURI(), "http://www.printtalk.org/schema_20");
	}

	/**
	 *
	 *
	 */
	@Test
	public void testSetVersion()
	{
		PrintTalk.setDefaultVersion(20);
		assertEquals(20, new PrintTalk().getVersion());
		assertEquals("2.0", new PrintTalk().getRoot().getAttribute(AttributeName.VERSION));

	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testGetPrintTalk()
	{
		final PrintTalk pt = new PrintTalk();
		assertEquals(pt.getPrintTalk(), pt);
	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testGetPrintTalkXML()
	{
		final KElement e = KElement.createRoot(PrintTalk.PRINT_TALK, null);
		final PrintTalk pt = new PrintTalk(e);
		assertEquals(pt.getPrintTalk(), pt);
		assertTrue(pt.getRoot() instanceof JDFElement);
	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testParseFile()
	{
		final PrintTalk pt = new PrintTalk();
		pt.write2File(sm_dirTestDataTemp + "ptk.ptk");
		final PrintTalk ptp = PrintTalk.parseFile(sm_dirTestDataTemp + "ptk.ptk");
		assertEquals(pt.getPayloadID(), ptp.getPayloadID());

	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testWrite2String()
	{
		final PrintTalk pt = new PrintTalk(null);
		assertNull(pt.write2String(2));
		final PrintTalk pt2 = new PrintTalk();
		assertNotNull(pt2.write2String(2));

	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testParseStream()
	{
		assertNull(PrintTalk.parseStream(null));
		assertNull(PrintTalk.parseStream(new ByteArrayInputStream("a".getBytes())));
		assertNotNull(PrintTalk.parseStream(new ByteArrayInputStream(new PrintTalk().write2String(2).getBytes())));
		final PrintTalk pt = new PrintTalk();
		pt.write2File(sm_dirTestDataTemp + "ptk.ptk");
		final PrintTalk ptp = PrintTalk.parseFile(sm_dirTestDataTemp + "ptk.ptk");
		assertEquals(pt.getPayloadID(), ptp.getPayloadID());

	}

	/**
	 *
	 * duh...
	 */
	@Test
	public void testEquals()
	{
		final PrintTalk pt = new PrintTalk();
		assertEquals(pt.getPrintTalk(), pt);
		assertEquals(pt.getPrintTalk().hashCode(), pt.hashCode());
		assertNotSame(pt, new PrintTalk(pt.getRoot().clone()));
		assertNotSame("there is a on in 4 billion chance that this may fail ;-)", pt.hashCode(), new PrintTalk(pt.getRoot().clone()).hashCode());
	}
}
