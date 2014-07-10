/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2014 The International Cooperation for the Integration of 
 * Processes in  Prepress, Press and Postpress (CIP4).  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        The International Cooperation for the Integration of 
 *        Processes in  Prepress, Press and Postpress (www.cip4.org)"
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of 
 *    Processes in  Prepress, Press and Postpress" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4",
 *    nor may "CIP4" appear in their name, without prior written
 *    permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For
 * details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR
 * THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the The International Cooperation for the Integration 
 * of Processes in Prepress, Press and Postpress and was
 * originally based on software 
 * copyright (c) 1999-2001, Heidelberger Druckmaschinen AG 
 * copyright (c) 1999-2001, Agfa-Gevaert N.V. 
 *  
 * For more information on The International Cooperation for the 
 * Integration of Processes in  Prepress, Press and Postpress , please see
 * <http://www.cip4.org/>.
 *  
 * 
 */
package org.cip4.printtalk;

import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.extensions.XJDF20;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;

/**
 *  
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public abstract class BusinessObject extends AbstractPrintTalk
{
	/**
	 * 
	 */
	public static final String ATTR_BUSINESSREFID = "BusinessRefID";
	/**
	 * 
	 */
	public static final String ATTR_BUSINESSID = "BusinessID";
	/** */
	public static String ATTR_CURRENCY = "Currency";
	/** */
	public static String ATTR_EXPIRES = "Expires";

	/**
	 * note that it is not necessarily always legal to add a JDF to an arbitrary bo
	 *  
	 * @param rootName
	 * @param iSkip
	 * @return
	 */
	public KElement getCreateJDFRoot(String rootName, int iSkip)
	{
		String schemaURL = JDFElement.getSchemaURL();
		if (XJDFHelper.XJDF.equals(rootName))
			schemaURL = XJDF20.getSchemaURL();
		KElement element = theElement.getCreateElement(rootName, schemaURL, iSkip);
		element.init();
		return element;
	}

	/**
	 * 
	 * @param theElement
	 */
	public BusinessObject(KElement theElement)
	{
		super(theElement);
	}

	/**
	 * 
	 * @see org.cip4.printtalk.AbstractPrintTalk#init()
	 */
	@Override
	public void init()
	{
		super.init();
		setBusinessID(theElement.getLocalName() + KElement.uniqueID(0));
	}

	/**
	 * 
	 * @return id
	 */
	public String getBusinessID()
	{
		return getAttribute(ATTR_BUSINESSID);
	}

	/**
	 * 
	 * @param id
	 */
	public void setBusinessID(String id)
	{
		setAttribute(ATTR_BUSINESSID, id);
	}

	/**
	 * 
	 * @return id
	 */
	public String getBusinessRefID()
	{
		return getAttribute(ATTR_BUSINESSREFID, null);
	}

	/**
	 * 
	 * @param id
	 */
	public void setBusinessRefID(String id)
	{
		theElement.setAttribute(ATTR_BUSINESSREFID, id);
	}

	/**
	 *  
	 * @param ref the bo to reference
	 */
	public void setRef(PrintTalk ref)
	{
		BusinessObject boRef = ref == null ? null : ref.getBusinessObject();
		if (ref != null)
			setBusinessRefID(boRef.getBusinessID());

	}

	/**
	 * factory method for printtalk business objects
	 *  
	 * @param e
	 * @return
	 */
	static BusinessObject getBusinessObject(KElement e)
	{
		if (e == null)
			return null;

		String boName = e.getLocalName();
		final BusinessObject businessObject;
		if (EnumBusinessObject.RFQ.name().equals(boName))
		{
			businessObject = new RFQ(e);
		}
		else if (EnumBusinessObject.Quotation.name().equals(boName))
		{
			businessObject = new Quotation(e);
		}
		else if (EnumBusinessObject.PurchaseOrder.name().equals(boName))
		{
			businessObject = new PurchaseOrder(e);
		}
		else if (EnumBusinessObject.Confirmation.name().equals(boName))
		{
			businessObject = new Confirmation(e);
		}
		else if (EnumBusinessObject.Cancellation.name().equals(boName))
		{
			businessObject = new Cancellation(e);
		}
		else if (EnumBusinessObject.Refusal.name().equals(boName))
		{
			businessObject = new Refusal(e);
		}
		else if (EnumBusinessObject.OrderStatusRequest.name().equals(boName))
		{
			businessObject = new OrderStatusRequest(e);
		}
		else if (EnumBusinessObject.OrderStatusResponse.name().equals(boName))
		{
			businessObject = new OrderStatusResponse(e);
		}
		else if (EnumBusinessObject.ProofApprovalRequest.name().equals(boName))
		{
			businessObject = new ProofApprovalRequest(e);
		}
		else if (EnumBusinessObject.ProofApprovalResponse.name().equals(boName))
		{
			businessObject = new ProofApprovalResponse(e);
		}
		else if (EnumBusinessObject.Invoice.name().equals(boName))
		{
			businessObject = new Invoice(e);
		}
		else if (EnumBusinessObject.ReturnJob.name().equals(boName))
		{
			businessObject = new ReturnJob(e);
		}
		else if (EnumBusinessObject.StockLevelRequest.name().equals(boName))
		{
			businessObject = new StockLevelRequest(e);
		}
		else if (EnumBusinessObject.StockLevelResponse.name().equals(boName))
		{
			businessObject = new StockLevelResponse(e);
		}
		else
		{
			throw new IllegalArgumentException("BusinessObject type not yet implemented: " + boName);
		}
		return businessObject;
	}

}
