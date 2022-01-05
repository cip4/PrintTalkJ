/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2021 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cip4.jdflib.core.JDFAudit;
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
import org.cip4.printtalk.builder.PrintTalkBuilder;
import org.cip4.printtalk.builder.PrintTalkBuilderFactory;
import org.junit.After;
import org.junit.Before;
import org.w3c.dom.Comment;
import org.w3c.dom.Node;

/**
 * testcase base class
 *
 * @author rainer prosi
 * @date Jun 13, 2014
 */
public abstract class PrintTalkTestCase
{
	/**
	 *
	 */
	public PrintTalkTestCase()
	{
		super();
		log = LogFactory.getLog(getClass());
	}

	static protected final String sm_dirTestData = getTestDataDir();
	static protected final String sm_dirTestDataTemp = sm_dirTestData + "temp" + File.separator;
	protected static final int defaultversion = PrintTalk.getDefaultVersion();
	protected final Log log;

	EnumVersion getXJDFVersion()
	{
		return EnumVersion.getEnum(2, defaultversion % 10);
	}

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
	@After
	public void tearDown() throws Exception
	{
		PrintTalk.setDefaultVersion(20);
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

	/**
	 *
	 * @param e
	 * @param startFirst if true include the enclosing element, if false exclude it
	 */
	protected void setSnippet(final AbstractPrintTalk e, final boolean startFirst)
	{
		setSnippet(e == null ? null : e.getRoot(), startFirst);
	}

	int getPrinttalkVersion(final PrintTalk pt)
	{
		return pt.getVersion();
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

	/**
	 *
	 * @param pt
	 * @param fail if true, failing is good
	 */
	protected void reparse(final BusinessObject pt, final boolean fail)
	{
		reparse(pt, defaultversion, fail);
	}

	/**
	 *
	 * @param bo
	 * @param ptv
	 * @param fail if true should fail
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

	/**
	 *
	 * @param ptk
	 * @param ptv
	 */
	static protected void schemaParse(final File ptk, final int ptv)
	{
		final JDFParser p = getSchemaParser(ptv);
		final JDFDoc xParsed = p.parseFile(ptk);
		final XMLDoc dVal = xParsed.getValidationResult();
		final String valResult = dVal.getRoot().getAttribute(XMLErrorHandler.VALIDATION_RESULT);
		if (!XMLErrorHandler.VALID.equals(valResult))
		{
			dVal.write2File(sm_dirTestDataTemp + ptk.getName() + ".validation.xml", 2, false);
		}
		assertEquals(XMLErrorHandler.VALID, valResult);
	}

	static protected JDFParser getSchemaParser(final int ptv)
	{
		final JDFParser parser = JDFParserFactory.getFactory().get();
		final int minor = ptv % 10;
		final File jdfxsd = getPTKSchema(minor);
		assertTrue(jdfxsd.canRead());
		final String url = UrlUtil.fileToUrl(jdfxsd, false);
		parser.setSchemaLocation(PrintTalk.getNamespaceURI(ptv), url);
		parser.addSchemaLocation(JDFElement.getSchemaURL(2, minor), StringUtil.replaceString(url, "PrintTalk.", "xjdf."));
		return parser;
	}

	protected static File getPTKSchema(final int minor)
	{
		final File jdfxsd = new File(sm_dirTestData + "schema" + "/Version_2_" + minor + File.separator + "PrintTalk.xsd");
		return jdfxsd;
	}

	protected String getPTNamespace()
	{
		return PrintTalk.getNamespaceURI(defaultversion);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception
	{
		//		LogConfigurator.configureLog(null, null);
		JDFElement.setLongID(false);
		JDFAudit.setStaticAgentName("MIS");
		JDFAudit.setStaticAgentVersion("2.1");
		PrintTalk.setDefaultVersion(21);
		PrintTalkBuilderFactory.getTheFactory().setVersion(PrintTalk.getDefaultVersion());
	}

}
