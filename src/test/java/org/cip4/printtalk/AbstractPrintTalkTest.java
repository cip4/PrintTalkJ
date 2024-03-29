/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2024 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.extensions.AuditPoolHelper;
import org.cip4.printtalk.HeaderBase.EnumHeaderType;
import org.junit.Test;

/**
 *
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public class AbstractPrintTalkTest extends PrintTalkTestCase
{
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
	 *
	 */
	@Test
	public void testGetComment()
	{
		final PrintTalk pt = new PrintTalk();
		assertNull(pt.getComment());
		pt.getCreateXJDFElement(ElementName.COMMENT, 0).setText("foo");
		assertEquals("foo", pt.getComment());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testSetComment()
	{
		final PrintTalk pt = new PrintTalk();
		assertNull(pt.getComment());
		pt.setComment("foo");
		assertEquals("foo", pt.getComment());
		pt.setComment(null);
		assertNull(pt.getElement(ElementName.COMMENT));
	}

	/**
	 *
	 *
	 */
	@Test
	public void testSetUserAgent()
	{
		final PrintTalk pt = new PrintTalk();
		assertNull(pt.getComment());
		pt.getCreateHeader(EnumHeaderType.From).setUserAgent("a");
		assertEquals("a", pt.getCreateHeader(EnumHeaderType.From).getUserAgent());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetElements()
	{
		final PrintTalk pt = new PrintTalk();
		assertNull(pt.getElements(null));
		pt.getCreateHeader(EnumHeaderType.From);
		assertEquals("Header", pt.getElements(null).get(0).getLocalName());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testGetAuditPool()
	{
		final PrintTalk pt = new PrintTalk();
		final AuditPoolHelper ap = pt.getCreateAuditPool();
		assertNotNull(ap);
		assertEquals(JDFElement.getSchemaURL(EnumVersion.Version_2_0), ap.getRoot().getNamespaceURI());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testCleanup()
	{
		final PrintTalk pt = new PrintTalk();
		final Credential to = pt.setCredential(EnumHeaderType.To, "abc", "efg");
		final Credential from = pt.setCredential(EnumHeaderType.From, "abc0", "efg0");
		pt.cleanUp();
		assertEquals("not sorted", to.theElement.getParentNode_KElement(), from.theElement.getParentNode_KElement().getNextSiblingElement());

	}

	/**
	 *
	 *
	 */
	@Test
	public void testCleanupVersion()
	{
		final PrintTalk pt = new PrintTalk();
		final Credential to = pt.setCredential(EnumHeaderType.To, "abc", "efg");
		final Credential from = pt.setCredential(EnumHeaderType.From, "abc0", "efg0");
		pt.cleanUp();
		assertEquals("2.2", pt.getAttribute(AttributeName.VERSION));

	}

	/**
	 *
	 *
	 */
	@Test
	public void testTypeSafe()
	{
		final KElement e = KElement.createRoot(PrintTalk.PRINT_TALK, null);
		final PrintTalk pt = new PrintTalk(e);
		assertTrue(pt.getRoot() instanceof JDFElement);

	}

	/**
	 * @see org.cip4.printtalk.PrintTalkTestCase#tearDown()
	 */
	@Override
	public void tearDown() throws Exception
	{
		super.tearDown();
		AbstractPrintTalk.setXJDFPrefix(false);
	}

}
