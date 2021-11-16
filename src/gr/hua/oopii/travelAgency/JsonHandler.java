package gr.hua.oopii.travelAgency;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JsonHandler<E> {

    String fileName;

    public JsonHandler() {
    }

    private void writeJSON(E source) throws IOException, JsonGenerationException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName), source);
    }

    @SuppressWarnings("unchecked")
    public E readJSON() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enableDefaultTyping();
        //TypeToken<ObjectType> typeToken = new TypeToken<ObjectType>(getClass()) { };
       //Type type = typeToken.getType(); // or getRawType() to return Class<? super T>

        TypeReference<E> myType2 = new TypeReference<>() {};
        //TypeReference<DataStructureType> myType1 = new TypeReference<>() {};

        //System.out.println(ObjectType.class);

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        return mapper.readValue(new File("arraylist.json"), mapper.getTypeFactory().
               constructCollectionType( List.class, myType2.getClass()));
=======
=======
>>>>>>> parent of 6bf619c (Json handling drafts)
=======
>>>>>>> parent of 6bf619c (Json handling drafts)
        //return mapper.readValue(new File("arraylist.json"), mapper.getTypeFactory().
               //constructCollectionType( List.class, myType2.getClass()));
>>>>>>> parent of 6bf619c (Json handling drafts)

        //mapper.getTypeFactory().constructParametricType(ObjectType.class, myType2);

        //return mapper.readValue(new File(fileName), myType2);
    }

}


/*
public class JacksonTester {

    public static void main(String args[]) throws InterruptedException {
        JacksonTester tester = new JacksonTester();
        ArrayList<Student> arraylist_students = new ArrayList<Student>();


        try {
    	  /* for (int i=20; i<30; i++) {
		         Student student = new Student();
		         student.setAge(i);
		         student.setName("name_"+(i-20));
		         Date date = new Date();
		         student.setTimestamp(date.getTime());
		         arraylist_students.add(student);
		         Thread.sleep(i);
		   }
		   tester.writeJSON(arraylist_students);

            arraylist_students=tester.readJSON();
                    System.out.println("The data of the array list is:\n"+arraylist_students);
                    System.out.println("The 1st object in the arraylist is: "+arraylist_students.get(1));
                    System.out.println("The 1st object in the arraylist is: "+arraylist_students.get(2));
                    System.out.println("The 1st object in the arraylist has timestamp: "+arraylist_students.get(2).getTimestamp());
                    System.out.println("The Name of 1st student is: "+((Student)arraylist_students.get(1)).getName());

                    Date dat=new Date(arraylist_students.get(2).getTimestamp());
                    dayOfWeek(dat);
                    }catch(JsonParseException e){
                    e.printStackTrace();
                    }catch(JsonMappingException e){
                    e.printStackTrace();
                    }catch(IOException e){
                    e.printStackTrace();
                    }
                    }
                    */



