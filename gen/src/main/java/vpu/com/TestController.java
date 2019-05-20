package vpu.com;

import org.jooq.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.PaymentRecord;

import static com.vpu.mp.db.shop.tables.Payment.PAYMENT;;


@RestController
public class TestController {

	DataManager dm = DataManager.instance();
	
	 @RequestMapping("/")
	public String index() {
		 
		 Result<PaymentRecord> rs = dm.db(2).fetch(PAYMENT);
		 System.out.println(rs);
		 
		 return "helloworld"; 
	}
}
