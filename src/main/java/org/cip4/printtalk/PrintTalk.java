/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2020 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.DocumentJDFImpl;
import org.cip4.jdflib.core.JDFDoc;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.core.XMLDoc;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.printtalk.HeaderBase.EnumHeaderType;

/**
 * Class to wrap the PrintTalk Root element
 *
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public class PrintTalk extends AbstractPrintTalk
{
	/**
	 *
	 */
	public static final String REQUEST = "Request";
	/**
	 *
	 */
	public static final String RESPONSE = "Response";
	/**
	 *
	 */
	public static final String PRINT_TALK = "PrintTalk";
	/**
	 *
	 */
	public static final String AGENT_ID = "jdf:AgentID";
	/**
	 *
	 */
	public static final String MIME_PRINT_TALK = "application/vnd.cip4-ptk+xml";

	/**
	 *
	 * business object types
	 *
	 * @author rainer prosi
	 * @date Jan 3, 2011
	 */
	public static enum EnumBusinessObject
	{
		/** */
		@Deprecated
		ArtDeliveryRequest, @Deprecated
		ArtDeliveryResponse,
		/** */
		RFQ,
		/** */
		Quotation,
		/** */
		PurchaseOrder,
		/** */
		Confirmation,
		/** */
		Cancellation,
		/** */
		Refusal,
		/** */
		OrderStatusRequest, OrderStatusResponse,
		/** */
		ProofApprovalRequest, ProofApprovalResponse,
		/** */
		Invoice,
		/** */
		ReturnJob,
		/** */
		StockLevelRequest, StockLevelResponse,

		ContentDelivery, ContentDeliveryResponse
	}

	private static int defaultVersion = 21;

	/**
	 * Getter for defaultVersion attribute.
	 *
	 * @return the defaultVersion
	 */
	public static int getDefaultVersion()
	{
		return defaultVersion;
	}

	/**
	 * Setter for defaultVersion attribute.
	 *
	 * @param defaultVersion the defaultVersion to set
	 */
	public static void setDefaultVersion(final int defaultVersion)
	{
		PrintTalk.defaultVersion = defaultVersion;
	}

	/**
	 *
	 * return the PrintTalk element for theElement id TheElement is a printtalk element
	 *
	 * @param theElement
	 * @return
	 */
	public static PrintTalk getPrintTalk(final KElement theElement)
	{
		if (theElement == null || !PRINT_TALK.equalsIgnoreCase(theElement.getLocalName()))
			return null;
		return new PrintTalk(theElement);
	}

	/**
	 *
	 * return the PrintTalk element for theElement id TheElement is a printtalk element
	 *
	 * @param theElement
	 * @return
	 */
	public static PrintTalk parseFile(final String name)
	{
		final KElement e = KElement.parseFile(name);
		return getPrintTalk(e);
	}

	/**
	 *
	 * generic cleanup routine
	 */
	@Override
	public void cleanUp()
	{
		if (theElement != null)
			theElement.renameAttribute("Timestamp", PrintTalkConstants.Timestamp);
		final BusinessObject bo = getBusinessObject();
		if (bo != null)
		{
			bo.cleanUp();
		}
		for (final EnumHeaderType ht : EnumHeaderType.values())
		{
			final HeaderBase b = getHeader(ht);
			if (b != null)
			{
				b.cleanUp();
			}
		}
		super.cleanUp();
	}

	/**
	 * create a printtalk helper
	 *
	 * @param theElement
	 */
	public PrintTalk(final KElement theElement)
	{
		super(theElement);
		setVersion(defaultVersion);
	}

	/**
	 * create a new printtalk helper
	 *
	 */
	public PrintTalk()
	{
		this(defaultVersion);
	}

	/**
	 * create a new printtalk helper
	 *
	 */
	public PrintTalk(final int version)
	{
		super(null);
		XMLDoc doc = new XMLDoc(PRINT_TALK, getNamespaceURI(version));
		doc = new JDFDoc(doc);
		((DocumentJDFImpl) doc.getMemberDocument()).bInitOnCreate = false;
		setRoot(doc.getRoot());
		init();
		setVersion(version);
	}

	/**
	 * checks whether kElem is in the PTK namespace
	 *
	 * @param ns the KElement to check
	 * @return boolean - true, if kElem is in the PTK namespace
	 */
	public static boolean isInPTKNameSpace(final String ns)
	{
		return ns != null && (ns.startsWith("http://www.printtalk.org/schema_20"));
	}

	/**
	 *
	 * get the correct namespace uri string
	 *
	 * @param version 10=1.0 etc
	 * @return
	 */
	public static String getNamespaceURI(int version)
	{
		if (version <= 0)
			version = defaultVersion;
		if (version > 20)
			version = 10 * (version / 10);
		return "http://www.printtalk.org/schema_" + version;
	}

	/**
	 *
	 * get the correct namespace uri string
	 *
	 * @return
	 */
	public String getNamespaceURI()
	{
		return getNamespaceURI(getVersion());
	}

	/**
	 * initialize a new printtalk
	 *
	 */
	@Override
	public void init()
	{
		super.init();
		final String dateTimeISO = new JDFDate().getDateTimeISO();
		theElement.setAttribute(PrintTalkConstants.Timestamp, dateTimeISO);
		setPayloadID("P" + KElement.uniqueID(0));
	}

	/**
	 *
	 * set a new credential identity value
	 *
	 * @param headerType type of header
	 * @param domain domain name
	 * @param identity Identity value
	 */
	public Credential setCredential(final EnumHeaderType headerType, final String domain, final String identity)
	{
		final HeaderBase header = getCreateHeader(headerType);
		final Credential setCredential = header.setCredential(domain, identity);
		if (header.getCredential(0) == null)
		{
			removeHeader(headerType);
			return null;
		}
		return setCredential;
	}

	/**
	 *
	 * set a new credential value
	 *
	 * @param headerType type of header
	 * @param domain domain name
	 * @return identity Identity value
	 */
	public String getCredentialIdentity(final EnumHeaderType headerType, final String domain)
	{
		final HeaderBase header = getHeader(headerType);
		return header == null ? null : header.getCredentialIdentity(domain);
	}

	/**
	 *
	 * get a credential
	 *
	 * @param headerType type of header
	 * @param domain domain name
	 * @return the credential
	 */
	public Credential getCredential(final EnumHeaderType headerType, final String domain)
	{
		final HeaderBase header = getHeader(headerType);
		return header == null ? null : header.getCredential(domain);
	}

	/**
	 * get the one and only header of type headerType
	 *
	 * @param headerType
	 * @return
	 */
	public HeaderBase getCreateHeader(final EnumHeaderType headerType)
	{
		HeaderBase header = getHeader(headerType);
		if (header == null && headerType != null)
		{
			header = new HeaderBase(getCreateXPathElement("Header/" + headerType.name()));
		}
		return header;
	}

	/**
	 * Setter for version that is used to generate the namespace
	 *
	 * @param version the version to set
	 */
	// TODO make attribute getter and setter in case we go down that route
	public void setVersion(final int version)
	{
		setAttribute(AttributeName.VERSION, version / 10 + "." + version % 10);
	}

	/**
	 * Getter for version attribute.
	 *
	 * @return the version
	 */
	public int getVersion()
	{
		return (int) Math.round(10 * getRealAttribute(AttributeName.VERSION, defaultVersion));
	}

	/**
	 * Getter for timestamp attribute. also checks for legacy "TimeStamp"
	 *
	 * @return the version
	 */
	public JDFDate getTimestamp()
	{
		if (theElement == null)
			return null;
		String ts = theElement.getNonEmpty(PrintTalkConstants.Timestamp);
		if (ts == null)
			ts = theElement.getNonEmpty("Timestamp");
		return JDFDate.createDate(ts);
	}

	/**
	 *
	 * appends a request with an initialized Business Object
	 *
	 * @param bo the business object type
	 * @param ref the printtalk object that this references
	 * @return the newly created BusinessObject, null if unsuccessful
	 * @throws IllegalArgumentException if bo already exists
	 */
	public BusinessObject appendRequest(final EnumBusinessObject bo, final PrintTalk ref) throws IllegalArgumentException
	{
		final BusinessObject oldBO = getBusinessObject();
		if (oldBO != null || getElement(RESPONSE) != null)
			throw new IllegalArgumentException("BusinessObject already exists: " + ((oldBO == null) ? "null" : oldBO.theElement.getLocalName()));

		final String boName = bo.name();
		final KElement e = theElement.getCreateElement(REQUEST).appendElement(boName);
		final BusinessObject businessObject = initBO(ref, e);
		return businessObject;
	}

	/**
	 *
	 * appends a synchronous response with an initialized Business Object
	 *
	 * @param bo the business object type
	 * @param ref the printtalk object that this references
	 * @return the newly created BusinessObject, null if unsuccessful
	 *
	 * @throws IllegalArgumentException if bo already exists
	 * @deprecated currently synchronous responses are not handled
	 */
	@Deprecated
	public BusinessObject appendResponse(final EnumBusinessObject bo, final PrintTalk ref) throws IllegalArgumentException
	{
		final BusinessObject oldBO = getBusinessObject();
		if (oldBO != null || getElement(REQUEST) != null || getElement(RESPONSE) != null)
			throw new IllegalArgumentException("BusinessObject already exists: " + ((oldBO == null) ? "null" : oldBO.theElement.getLocalName()));

		final String boName = bo.name();
		final KElement e = theElement.getCreateElement(RESPONSE).appendElement(boName);
		final BusinessObject businessObject = initBO(ref, e);
		return businessObject;
	}

	private BusinessObject initBO(final PrintTalk ref, final KElement e)
	{
		final BusinessObject businessObject = BusinessObject.getBusinessObject(e);
		businessObject.init();
		businessObject.setRef(ref);
		return businessObject;
	}

	/**
	 * get the business object from a request
	 *
	 * @return
	 */
	public BusinessObject getBusinessObject()
	{
		final KElement request = getElement(REQUEST);
		final KElement oldBO = request == null ? null : request.getFirstChildElement();
		return BusinessObject.getBusinessObject(oldBO);
	}

	/**
	 * get the header from a request
	 *
	 * @param headerType the type (From / to / Sender)
	 * @return
	 */
	public HeaderBase getHeader(final EnumHeaderType headerType)
	{
		final KElement request = headerType == null ? null : getElement(PrintTalkConstants.Header);
		final KElement header = request == null ? null : request.getElement(headerType.name());
		return header == null ? null : new HeaderBase(header);
	}

	/**
	 * get the header from a request
	 *
	 * @param headerType the type (From / to / Sender)
	 * @return
	 */
	public HeaderBase removeHeader(final EnumHeaderType headerType)
	{
		final KElement request = headerType == null ? null : getElement(PrintTalkConstants.Header);
		final KElement header = request == null ? null : request.getElement(headerType.name());
		return header == null ? null : new HeaderBase(header.deleteNode());
	}

	/**
	 * @see org.cip4.printtalk.AbstractPrintTalk#getPrintTalk()
	 */
	@Override
	public PrintTalk getPrintTalk()
	{
		return this;
	}

	/**
	 *
	 * @return
	 */
	public String getPayloadID()
	{
		return getAttribute(PrintTalkConstants.payloadID);
	}

	/**
	 *
	 * @param payloadID
	 */
	public void setPayloadID(final String payloadID)
	{
		setAttribute(PrintTalkConstants.payloadID, payloadID);
	}

	/**
	 * @param filename
	 * @return
	 * @see org.cip4.jdflib.core.KElement#write2File(java.lang.String)
	 */
	public boolean write2File(final String filename)
	{
		return theElement == null ? false : theElement.getOwnerDocument_KElement().write2File(filename, 2, false);
	}

	/**
	 * set attribute ICSVersions
	 *
	 * @param value the value to set the attribute to
	 */
	public void setICSVersions(final VString value)
	{
		setAttribute(AttributeName.ICSVERSIONS, value == null ? null : value.getString());
	}

	/**
	 * get NMTOKENS attribute ICSVersions
	 *
	 * @param bInherit if true recurse through ancestors when searching
	 * @return VString - attribute value
	 *
	 * @default getICSVersions(false)
	 */
	public VString getICSVersions()
	{
		return VString.getVString(getAttribute(AttributeName.ICSVERSIONS), null);
	}

}
