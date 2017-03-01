package spring.cloud.dto;

public enum CollectTypeEnum {
	TYPE0{
        //实现抽象方法  
        public String getDescription()  
        {  
        	return "总科目金额及其子科目金额";
        }  
        public Short getValue()  
        {  
            return 0;  
        } 
    },
    TYPE1{
        //实现抽象方法  
        public String getDescription()  
        {  
        	return "汇总科目金额";
        }  
        public Short getValue()  
        {  
        	return 1;  
        } 
    };
    public abstract String getDescription();  
    public abstract Short getValue(); 
    
    public static CollectTypeEnum getEnum(String value){
    	for(CollectTypeEnum ctEnum : CollectTypeEnum.values()){
    		if(ctEnum.getValue().toString().equals(value)){
    			return ctEnum;
    		}
    	}
		return null;
    }
}
