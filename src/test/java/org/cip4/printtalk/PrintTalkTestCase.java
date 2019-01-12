/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2014 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.JDFElement.EnumVersion;
import org.cip4.jdflib.core.JDFParser;
import org.cip4.jdflib.core.JDFParserFactory;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.core.XMLErrorHandler;
import org.cip4.jdflib.util.StringUtil;
import org.cip4.jdflib.util.UrlUtil;
import org.cip4.jdflib.util.logging.LogConfigurator;
import org.cip4.printtalk.builder.PrintTalkBuilder;
import org.cip4.printtalk.builder.PrintTalkBuilderFactory;
import org.w3c.dom.Comment;
import org.w3c.dom.Node;

import junit.framework.TestCase;

/**
 * testcase base class
 *
 * @author rainer prosi
 * @date Jun 13, 2014
 */
public abstract class PrintTalkTestCase extends TestCase
{
	static protected final EnumVersion defaultVersion = EnumVersion.Version_1_5;
	static protected final String sm_dirTestData = getTestDataDir();
	static protected final String sm_dirTestDataTemp = sm_dirTestData + "temp" + File.separator;
	protected static final int defaultversion = 20;

	private static String getTestDataDir()
	{

		String path = "test" + File.separator + "data";
		final File dataFile = new File(path);
		if (!dataFile.isDirectory()) // legacy - pre maven file structure support
			path = PrintTalkTestCase.class.getResource("/data").getPath();
		path = FilenameUtils.normalize(path) + File.separator;

		return path;

	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception
	{
		PrintTalk.setDefaultVersion(20);
		super.tearDown();
	}

	/**
	 *
	 * @param e
	 * @param startFirst if true include the enclosing element, if false exclude it
	 */
	protected void setSnippet(final KElement e, final boolean startFirst)
	{
		if (e != null)
		{
			final Node parent = e.getParentNode();
			final String start = " START SNIPPET ";
			final String end = " END SNIPPET ";
			Comment newChild = e.getOwnerDocument().createComment(startFirst ? start : end);
			parent.insertBefore(newChild, e);
			newChild = e.getOwnerDocument().createComment(startFirst ? end : start);
			parent.insertBefore(newChild, e.getNextSibling());
		}
	}

	int getPrinttalkVersion(final PrintTalk pt)
	{
		final String s = pt.getNamespaceURI();
		return StringUtil.parseInt(StringUtil.token(s, -1, "_"), 0);
	}

	/**
	 *
	 * @param pt
	 */
	protected void writeExample(final PrintTalk pt, final String file)
	{
		final String absolute = sm_dirTestDataTemp + "printtalkexamples/" + file;
		final boolean written = pt.write2File(absolute);
		assertTrue(written);
		final int ptv = getPrinttalkVersion(pt);
		final JDFParser p = getSchemaParser(ptv);
		final JDFDoc xParsed = p.parseFile(absolute);
		final XMLDoc dVal = xParsed.getValidationResult();
		final String valResult = dVal.getRoot().getAttribute(XMLErrorHandler.VALIDATION_RESULT);
		if (!XMLErrorHandler.VALID.equals(valResult))
		{
			dVal.write2File(UrlUtil.newExtension(absolute, "val.xml"), 2, false);
		}
		assertEquals(XMLErrorHandler.VALID, valResult);

	}

	protected void reparse(final BusinessObject pt, final boolean fail)
	{
		reparse(pt, defaultversion, fail);
	}

	/**
	 *
	 * @param fail TODO
	 * @param pt
	 */
	static protected void reparse(final BusinessObject bo, final int ptv, final boolean fail)
	{
		final PrintTalkBuilder ptb = PrintTalkBuilderFactory.getTheFactory().getBuilder();
		ptb.resetInstance();
		ptb.setVersion(ptv);
		ptb.setFromURL("https://customer.com");
		ptb.setToURL("https://printer.com");

		final PrintTalk pt = ptb.getPrintTalk();
		pt.getRoot().getCreateElement(PrintTalk.REQUEST).copyElement(bo.getRoot(), null);
		pt.getBusinessObject().init();
		final String written = pt.getRoot().toXML();
		assertNotNull(written);
		final JDFParser p = getSchemaParser(ptv);
		final JDFDoc xParsed = p.parseString(written);
		final XMLDoc dVal = xParsed.getValidationResult();
		final String valResult = dVal.getRoot().getAttribute(XMLErrorHandler.VALIDATION_RESULT);
		if (fail)
		{
			assertFalse(XMLErrorHandler.VALID.equals(valResult));
		}
		else
		{
			if (!XMLErrorHandler.VALID.equals(valResult))
			{
				dVal.write2File(sm_dirTestDataTemp + pt.getRoot().getLocalName() + ".validation.xml", 2, false);
			}
			assertEquals(XMLErrorHandler.VALID, valResult);
		}
	}

	static JDFParser getSchemaParser(final int ptv)
	{
		final JDFParser parser = JDFParserFactory.getFactory().get();
		final int minor = ptv % 10;
		final File jdfxsd = new File(sm_dirTestData + "schema" + "/Version_2_" + minor + File.separator + "xjdf.xsd");
		assertTrue(jdfxsd.canRead());
		final String url = UrlUtil.fileToUrl(jdfxsd, false);
		parser.setSchemaLocation(JDFElement.getSchemaURL(2, minor), url);
		parser.addSchemaLocation(PrintTalk.getNamespaceURI(ptv), StringUtil.replaceString(url, "xjdf.", "PrintTalk."));
		return parser;
	}

	protected String getPTNamespace()
	{
		return PrintTalk.getNamespaceURI(defaultversion);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		LogConfigurator.configureLog(null, null);
		JDFElement.setLongID(false);
		super.setUp();
	}

}
