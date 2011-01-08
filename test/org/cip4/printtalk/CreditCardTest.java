package org.cip4.printtalk;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;

public class CreditCardTest extends JDFTestCaseBase
{

	/**
	 * 
	 */
	public void testSetExpires()
	{
		Invoice invoice = (Invoice) new PrintTalk().appendRequest(EnumBusinessObject.Invoice, null);
		JDFDate expires = new JDFDate();
		CreditCard cc = invoice.getCreatePricing().getCreatePayment().getCreateCreditCard();
		cc.setExpires(expires);
		
		System.out.println("cc.getExpires: " + cc.getExpires());
		assertEquals(expires, cc.getExpires());
	}

}
