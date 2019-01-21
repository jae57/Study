package bind;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

public class ServletRequestDataBinder {
    // request, vo.Member.class, "member" 가 매개변수값으로 넘겨질 경우
    public static Object bind(ServletRequest request, Class<?> dataType, String dataName) throws Exception{
        if(isPrimitiveType(dataType)){
            return createValueObject(dataType, request.getParameter(dataName));
        }
        // 사용자의 request로 넘어온 "name", "email", "password"
        Set<String> paramNames = request.getParameterMap().keySet();
        // vo.Member의 인스턴스 하나 만들기
        Object dataObject = dataType.getDeclaredConstructor().newInstance();
        // 세터 메서드 참조변수 생성
        Method m = null;

        // "name", "email", "password" 각각에 해당하는 세터메서드를 찾을거임. Member클래스에서..
        // Member클래스에 선언된 setter클래스들은 모두 리턴값이 Member클래스임
        for(String paramName: paramNames){
            m = findSetter(dataType, paramName);
            if(m!= null){ // 일치하는 메서드를 찾았으면, 예를 들어 "name"이면 setName(String name) 메서드가 찾아짐.
                // 우선, createValueObject로 매개변수 ( String, 실제 사용자가 request로 보낸 value ) 넘김
                // String 이니까 그냥 나옴.
                m.invoke(dataObject, createValueObject(m.getParameterTypes()[0], request.getParameter(paramName)));
                // 결국 m.invoke(Member, "이름") 이렇게 됨.  receiver과 매개변수값을 의미함.
                // 해당 세터메서드를 실행시킨 결과를 dataObject로 받아온다는 뜻
            }
        }

        return dataObject;
    }

    private static boolean isPrimitiveType(Class<?> type){
        if(type.getName().equals("int") || type == Integer.class ||
                type.getName().equals("long") || type == Long.class ||
                type.getName().equals("float") || type == Float.class ||
                type.getName().equals("double") || type == Double.class ||
                type.getName().equals("boolean") || type == Boolean.class ||
                type == Date.class || type == String.class) {
            return true;
        }
        return false;
    }

    private static Object createValueObject(Class<?> type, String value){
        if(type.getName().equals("int") || type == Integer.class){
            return Integer.parseInt(value);
        } else if (type.getName().equals("float") || type == Float.class) {
            return Float.parseFloat(value);
        } else if (type.getName().equals("double") || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type.getName().equals("long") || type == Long.class) {
            return Long.parseLong(value);
        } else if (type.getName().equals("boolean") || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == Date.class) {
            return java.sql.Date.valueOf(value);
        } else {
            return value;
        }
    }

    private static Method findSetter(Class<?> type, String name){
        Method[] methods = type.getMethods();

        String propName = null;
        for(Method m : methods){
            if(!m.getName().startsWith("set")) continue;

            propName=m.getName().substring(3);
            if(propName.toLowerCase().equals(name.toLowerCase())){
                return m;
            }
        }
        return null;
    }
}
