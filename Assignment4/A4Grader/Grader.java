import java.util.stream.IntStream;

public class Grader {

	public static void main(String[] args) {
		
		System.out.println("This is the COMP251 Assignment 4 Grader");
		
		GraderManager.Manager make = new GraderManager.Manager();
		
		GraderManager naive = make.manager("Naive");
		
		naive.addGradings(
			linker -> {
				linker.info("Testing naive - default");
				int reduce = IntStream.range(0, 127).reduce(1,
	    			(acc, i)->
	    				acc * (IntStream
    						.range(0, 127)
    						.allMatch(j->i*j == Multiply.naive(7, i,j)[0]) ? 1:0));
				return new GraderManager.Mark(reduce, 1);
			},
			linker->{
				linker.info("Testing naive - one positive, one negative");
				int reduce = IntStream.range(0, 127).reduce(1,
		    			(acc, i)->
		    				acc * (IntStream
	    						.range(-126, 0)
	    						.allMatch(j->i*j == Multiply.naive(7, i,j)[0]) ? 1:0));
					return new GraderManager.Mark(reduce*0.5, 0.5);
			},
			linker->{
				linker.info("Testing naive - negative*negative");
				int reduce = IntStream.range(0, 127).reduce(1,
		    			(acc, i)->
		    				acc * (IntStream
	    						.range(-126, 0)
	    						.allMatch(j->i*j == Multiply.naive(7, i,j)[0]) ? 1:0));
					return new GraderManager.Mark(reduce*0.5, 0.5);
			},
			linker ->{
				linker.info("Testing naive - number of bits: 1");
				int product = Multiply.naive(1, 8, 8)[0];
                                System.out.println(product);
				return product==64? 
						new GraderManager.Mark(1, 1):
						new GraderManager.Mark(0, 1);
			},
			linker -> {
				linker.info("Testing naive - number of bits: even");
				int product = Multiply.naive(4, 12, 12)[0];
				System.out.println(product);
				return  product==144? 
						new GraderManager.Mark(1, 1):
						new GraderManager.Mark(0, 1);
			},
			linker -> {
				linker.info("Testing naive - number of bits: odd");
				int product = Multiply.naive(3, 12, 12)[0];
				System.out.println(product);
				return  product==144? 
						new GraderManager.Mark(1, 1):
						new GraderManager.Mark(0, 1);
			},
			linker ->{
				linker.info("Testing naive - op counts : 1 bit");
				int count = Multiply.naive(1, 8, 8)[1];
				return count==1? 
						new GraderManager.Mark(1, 1):
						new GraderManager.Mark(0, 1);
			},
			linker ->{
				linker.info("Testing naive - op counts : even bits");
				int count = Multiply.naive(4, 1000, 1000)[1];
				return count==34? 
						new GraderManager.Mark(1, 1):
						new GraderManager.Mark(0, 1);
			},
			linker ->{
				linker.info("Testing naive - op counts : odd bits");
				int count = Multiply.naive(5, 1000, 1000)[1];
				
				// recursions on left right splits are performed with exactly required amount of bits
				int count_left_right_specific = 100;
				// recursions of left right splits are performed assuming the ceiling of n bits
				int count_assumes_even_splits = 145;
				
				return count==count_left_right_specific? 
						new GraderManager.Mark(1, 1):
							count==count_assumes_even_splits?
								new GraderManager.Mark(1, 1):
								new GraderManager.Mark(0, 1);
			}
		);
		
		make.show(naive);
		
		GraderManager karatsuba = make.manager("Karatsuba");
		
		karatsuba.addGradings(
				linker -> {
					linker.info("Testing karatsuba - default");
					int reduce = IntStream.range(0, 127).reduce(1,
		    			(acc, i)->
		    				acc * (IntStream
	    						.range(0, 127)
	    						.allMatch(j->i*j == Multiply.karatsuba(7, i,j)[0]) ? 1:0));
					return new GraderManager.Mark(reduce, 1);
				},
				linker->{
					linker.info("Testing karatsuba - one positive, one negative");
					int reduce = IntStream.range(0, 127).reduce(1,
			    			(acc, i)->
			    				acc * (IntStream
		    						.range(-126, 0)
		    						.allMatch(j->i*j == Multiply.karatsuba(7, i,j)[0]) ? 1:0));
						return new GraderManager.Mark(reduce*0.5, 0.5);
				},
				linker->{
					linker.info("Testing karatsuba - negative*negative");
					int reduce = IntStream.range(0, 127).reduce(1,
			    			(acc, i)->
			    				acc * (IntStream
		    						.range(-126, 0)
		    						.allMatch(j->i*j == Multiply.karatsuba(7, i,j)[0]) ? 1:0));
						return new GraderManager.Mark(reduce*0.5, 0.5);
				},
				linker ->{
					linker.info("Testing karatsuba - number of bits: 1");
					int product = Multiply.karatsuba(1, 8, 8)[0];
				        System.out.println(product);
					return product==64? 
							new GraderManager.Mark(1, 1):
							new GraderManager.Mark(0, 1);
				},
				linker -> {
					linker.info("Testing karatsuba - number of bits: even");
					int product = Multiply.karatsuba(4, 12, 12)[0];
			                System.out.println(product);
					return  product==144? 
							new GraderManager.Mark(1, 1):
							new GraderManager.Mark(0, 1);
				},
				linker -> {
					linker.info("Testing karatsuba - number of bits: odd");
					int product = Multiply.karatsuba(3, 12, 12)[0];
                                	System.out.println(product);					
					return  product==144? 
							new GraderManager.Mark(1, 1):
							new GraderManager.Mark(0, 1);
				},
				linker ->{
					linker.info("Testing karatsuba - op counts : 1 bit");
					int count = Multiply.karatsuba(1, 8, 8)[1];
					return count==1? 
							new GraderManager.Mark(1, 1):
							new GraderManager.Mark(0, 1);
				},
				linker ->{
					linker.info("Testing karatsuba - op counts : even bits");
					int count = Multiply.karatsuba(4, 1000, 1000)[1];
					return count==39? 
							new GraderManager.Mark(1, 1):
							new GraderManager.Mark(0, 1);
				},
				linker ->{
					linker.info("Testing karatsuba - op counts : odd bits");
					int count = Multiply.karatsuba(5, 1000, 1000)[1];
					
					// recursions on left right splits are performed with exactly required amount of bits
					int count_left_right_specific = 89;
					// recursions of left right splits are performed assuming the ceiling of n bits
					int count_assumes_even_splits = 135;
					
					return count==count_left_right_specific? 
							new GraderManager.Mark(1, 1):
								count==count_assumes_even_splits?
									new GraderManager.Mark(1, 1):
									new GraderManager.Mark(0, 1);
				}
		);
		
		make.show(karatsuba);
		
		System.out.println("Total score: "+ 
				(naive.reweightedGrade(25).combine(karatsuba.reweightedGrade(25))).score) ;
		System.out.println("Done");
	}
	
	
}
