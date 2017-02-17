package spring.cloud.dto;

public enum AmountExtractDirectionEnum {
    DEBIT {
        //实现抽象方法  
        public String getDescription() {
            return "借";
        }

        public Short getValue() {
            return 0;
        }
    },
    CREDIT {
        //实现抽象方法  
        public String getDescription() {
            return "贷";
        }

        public Short getValue() {
            return 1;
        }
    },
    DEBIT_MINUS_CREDIT {
        //实现抽象方法  
        public String getDescription() {
            return "借减贷";
        }

        public Short getValue() {
            return 2;
        }
    },
    CREDIT_MINUS_DEBIT {
        //实现抽象方法
        public String getDescription() {
            return "贷减借";
        }

        public Short getValue() {
            return 3;
        }
    };

    public abstract String getDescription();

    public abstract Short getValue();
    
    public static AmountExtractDirectionEnum getEnum(String value){
    	for(AmountExtractDirectionEnum aedEnum : AmountExtractDirectionEnum.values()){
    		if(aedEnum.getValue().toString().equals(value)){
    			return aedEnum;
    		}
    	}
		return null;
    }
}
