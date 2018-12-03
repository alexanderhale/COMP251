package A2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import A2.GraderManager.Mark;

public class Grader {

	public static void main(String[] args) {
		
		System.out.println("This is the COMP251 Assignment 2 Grader");
		
		System.out.println("Question 1 - Disjoint Sets");
		GraderManager ds_grade = new GraderManager();
		
		
		// Test non-action state
		ds_grade.addGradings(
			linker->{
				linker.info("Testing non-action state; find does not change states");
				DisjointSets ds = new DisjointSets(10);
				boolean nochange = IntStream.range(0, 10)
					.mapToObj(i->i==ds.find(i))
					.reduce((acc,x)-> acc&x ).get();
				return nochange ? new Mark(1, 1) : new Mark(0, 1);
			},
			
			linker->{
				linker.info("Testing simple union");
				DisjointSets ds = new DisjointSets(2);
				ds.union(0, 1);
				return ds.find(0)==ds.find(1)? new Mark(1, 1) : new Mark(0, 1);
			},
			
			linker->{
				linker.info("Testing repeated union");
				DisjointSets ds = new DisjointSets(3);
				ds.union(0, 1);
				int f0_0 = ds.find(0);
				int f1_0 = ds.find(1);
				ds.union(0, 1);
				int f0_1 = ds.find(0);
				int f1_1 = ds.find(1);
				return f0_0==f0_1 
						&& f0_1==f1_0 
						&& f1_0==f1_1? new Mark(1, 1): new Mark(0, 1);
			},
			
			linker->{
				linker.info("Testing preservation of representation");
				DisjointSets ds = new DisjointSets(1337);
				int[] representatives = new int[1336];
				for (int i = 0; i < representatives.length; i++) {
					ds.union(i+1, 0);
					representatives[i] = ds.find(0);
				}
				boolean allSame = IntStream.range(1, 1336).allMatch(i->representatives[i]==representatives[i-1]);
				return allSame? new Mark(1, 1) : new Mark(0, 1);
			},
			linker->{
				linker.info("Testing union by rank");
				DisjointSets ds = new DisjointSets(8);
				// generate rank 2
				ds.union(2, 3);
				ds.union(2, 1);
				ds.union(4, 5);
				ds.union(3, 1);
				ds.union(2, 4);
				int represent2 = ds.find(2);
				// generate rank 1
				ds.union(6, 7);
				// union the 2
				ds.union(2, 6);
				
				return ds.find(2) == represent2? new Mark(2, 2): new Mark(0, 1);
			}
		);
		
		
		ds_grade.grade();
		ds_grade.displayGrades();
		System.out.println("Disjoint sets: "+ds_grade.grade);
		
		System.out.println("Question 2 - MST");
		
		GraderManager mst_grade = new GraderManager();
		
		mst_grade.addGradings(
			linker ->{
				linker.info("Testing isSafe");
				DisjointSets ds0 = new DisjointSets(2);
				DisjointSets ds1 = new DisjointSets(2);
				
				ds0.union(0, 1);
				boolean isSafe0 = Kruskal.IsSafe(ds0, new Edge(0, 1, 1));
				boolean isSafe1 = Kruskal.IsSafe(ds1, new Edge(0, 1, 1));
				
				return !isSafe0 && isSafe1? new Mark(1, 1) : new Mark(0, 1);
			},
			linker->{
				linker.info("Testing kruskal; (case: 3-star)");
				WGraph g = new WGraph();
				Edge e0 = new Edge(0, 1, 1);
				Edge e1 = new Edge(0, 2, 1);
				Edge e2 = new Edge(0, 3, 1);
				Edge[] es = new Edge[] {e0, e1, e2};
				Arrays.stream(es).forEach(e->g.addEdge(e));
				WGraph kruskal = Kruskal.kruskal(g);
				
				return kruskal.listOfEdgesSorted().size() == 3
						&& kruskal.listOfEdgesSorted().contains(e0)
						&& kruskal.listOfEdgesSorted().contains(e1)
						&& kruskal.listOfEdgesSorted().contains(e2)? 
								new Mark(1, 1):
								new Mark(0, 1);
			},
			linker->{
				linker.info("Testing kruskal; (case: 4 pan graph)");
				WGraph g = new WGraph();
				Edge[] es = new Edge[] {
						new Edge(0, 1, 1),
						new Edge(0, 2, 1),
						new Edge(1, 3, 1),
						new Edge(2, 3, 1),
						new Edge(3, 4, 1),
				};
				Arrays.stream(es).forEach(e->g.addEdge(e));
				WGraph kruskal = Kruskal.kruskal(g);
				
				List<Edge> edges = kruskal.listOfEdgesSorted();
				return edges.size() == 4
						&& edges.contains(es[4])? 
								new Mark(1, 1):
									new Mark(0, 1);
			},
			linker->{
				linker.info("Testing kruskal; (case: disjoint)");
				WGraph g = new WGraph();
				Edge[] es = new Edge[] {
						new Edge(0, 1, 1),
						new Edge(0, 2, 1),
						new Edge(1, 3, 1),
						new Edge(2, 3, 1),
						new Edge(3, 4, 1),
						
						new Edge(5, 6, 1),
						new Edge(5, 7, 1),
						new Edge(5, 8, 1),
				};
				Arrays.stream(es).forEach(e->g.addEdge(e));
				WGraph kruskal = Kruskal.kruskal(g);
				
				List<Edge> edges = kruskal.listOfEdgesSorted();
				return edges.size() == es.length-1
						&& edges.contains(es[4])
						&& edges.contains(es[5])
						&& edges.contains(es[6])
						&& edges.contains(es[7])? 
								new Mark(1, 1):
									new Mark(0, 1);
			},
			linker->{
				linker.info("Testing kruskal; (case: dumbbell)");
				WGraph g = new WGraph();
				Edge[] es = new Edge[] {
						new Edge(0, 1, 1),
						new Edge(0, 2, 1),
						new Edge(1, 3, 1),
						new Edge(2, 3, 1),
						
						new Edge(3, 4, 1),
						
						new Edge(4, 5, 1),
						new Edge(4, 6, 1),
						new Edge(5, 7, 1),
						new Edge(6, 7, 1),
				};
				Arrays.stream(es).forEach(e->g.addEdge(e));
				WGraph kruskal = Kruskal.kruskal(g);
				
				List<Edge> edges = kruskal.listOfEdgesSorted();
				return edges.size() == es.length-2
						&& edges.contains(es[4])? 
								new Mark(1, 1):
									new Mark(0, 1);
			},
			linker->{
				linker.info("Testing kruskal; (case: dumbbell with distractors)");
				WGraph g = new WGraph();
				Edge[] es = new Edge[] {
						new Edge(0, 1, 1),
						new Edge(0, 2, 1),
						// horizontal distractor
						new Edge(0, 3, 1),
						new Edge(1, 3, 1),
						new Edge(2, 3, 1),
						
						new Edge(3, 4, 1),
						
						new Edge(4, 5, 1),
						new Edge(4, 6, 1),
						//vertical distractor
						new Edge(5, 6, 1),
						new Edge(5, 7, 1),
						new Edge(6, 7, 1),
				};
				Arrays.stream(es).forEach(e->g.addEdge(e));
				WGraph kruskal = Kruskal.kruskal(g);
				
				List<Edge> edges = kruskal.listOfEdgesSorted();
				return edges.size() == es.length-4
						&& edges.contains(es[5])? 
								new Mark(1, 1):
									new Mark(0, 1);
			}
		);
		
		mst_grade.grade();
		mst_grade.displayGrades();
		System.out.println("MST: "+ mst_grade.grade);
		System.out.println("Question 3");
		
		GraderManager greedy_grade = new GraderManager();
		int[] weights = new int[] {23, 60, 14, 25, 7}; 
		int[] deadlines = new int[] {3, 1, 2, 1, 3};
		int m = weights.length;
		
		
		
		greedy_grade.addGradings(
			linker->{
				linker.info("Testing correct length of output");
				HW_Sched schedule =  new HW_Sched(weights, deadlines, m);
				int[] res = schedule.SelectAssignments();
				
				return res.length==schedule.m? new Mark(1, 1): new Mark(0, 1);
			},
			linker->{
				linker.info("Testing for negative numbers");
				HW_Sched schedule =  new HW_Sched(weights, deadlines, m);
				int[] res = schedule.SelectAssignments();
				boolean allNonNegative = schedule.Assignments.stream()
													.map(a->res[a.number])
													.allMatch(entry->entry>=0);
				return allNonNegative? new Mark(1, 1): new Mark(0, 1);
			},
			linker->{
				linker.info("Testing for slot conflicts");
				HW_Sched schedule =  new HW_Sched(weights, deadlines, m);
				int[] res = schedule.SelectAssignments();
				AtomicInteger[] timeslots = new AtomicInteger[schedule.lastDeadline];
				IntStream.range(0, timeslots.length).forEach(i->timeslots[i]=new AtomicInteger(0));
				schedule.Assignments.stream()
					.map(a->res[a.number])
					.filter(entry->entry>0)
					.forEach(pos_entry->timeslots[pos_entry-1].getAndIncrement());
				boolean unconflicted = Arrays.stream(timeslots).allMatch(slot->slot.get()<=1);
				return unconflicted? new Mark(1, 1): new Mark(0, 1);
			},
			linker->{
				linker.info("Testing for completion before deadline");
				HW_Sched schedule =  new HW_Sched(weights, deadlines, m);
				int[] res = schedule.SelectAssignments();
				boolean onTime = schedule.Assignments.stream().allMatch(a->res[a.number]<=a.deadline);
				return onTime? new Mark(1, 1): new Mark(0, 1);
			},
			linker->{
				linker.info("Testing scheduling; (case: no conflicts)");
				// should be no conflicts
				int[] w = new int[] {3,2,1};
				int[] t = new int[] {3,2,1};
				HW_Sched schedule = new HW_Sched(w, t, w.length);
				int[] res = schedule.SelectAssignments();
				int[] target = new int[] {3,2,1};
				boolean matchTarget = IntStream.range(0, res.length).allMatch(i-> res[i]==target[i]);
				return matchTarget? new Mark(2,2): new Mark(0,2);
			},
			linker->{
				linker.info("Testing scheduling; (case : weight advantage)");
				// conflict for second slot
				int[] w = new int[] {3,2,9,1};
				int[] t = new int[] {3,2,2,1};
				HW_Sched schedule = new HW_Sched(w, t, w.length);
				int[] res = schedule.SelectAssignments();
				int[] target = new int[] {3,1,2,0};
				boolean matchTarget = IntStream.range(0, res.length).allMatch(i-> res[i]==target[i]);
				return matchTarget? new Mark(2,2): new Mark(0,2);
			},
			linker->{
				linker.info("Testing scheduling; (case: homework overwhelming)");
				int[] w = new int[] {9,19,9,9,19,9};
				int[] t = new int[] {2,2,2,2,2,2};
				HW_Sched schedule = new HW_Sched(w, t, w.length);
				int[] res = schedule.SelectAssignments();
				int n_nonzero = Arrays.stream(res).map(res_i->res_i>0?1:0).sum();
				return n_nonzero==2? new Mark(1,1): new Mark(0,1);
			},
			linker->{
				linker.info("Testing scheduling; (case: slacker's paradise)");
				int[] w = new int[] {10,10};
				int[] t = new int[] {3,100};
				HW_Sched schedule = new HW_Sched(w, t, w.length);
				int[] res = schedule.SelectAssignments();
				int n_nonzero = Arrays.stream(res).map(res_i->res_i>0?1:0).sum();
				
				return n_nonzero==2? new Mark(1,1): new Mark(0,1);
			},
			linker->{
				linker.info("Testing scheduling; (case: overlapping conflicts)");
				int[] w = new int[] {1,2,3,4};
				int[] t = new int[] {1,1,2,2};
				HW_Sched schedule = new HW_Sched(w, t, w.length);
				int[] res = schedule.SelectAssignments();
				int score = IntStream.range(0, res.length).map(i->res[i]>0? w[i]:0).sum();
				return score==6? new Mark(1,2):
					   score==7? new Mark(2,2):
						   new Mark(0,2);
			}
		);
		
		greedy_grade.grade();
		greedy_grade.displayGrades();
		System.out.println("Greedy: "+greedy_grade.grade);
		
		
		System.out.println("Total Score: "+ 
				(ds_grade.reweightedGrade(20).combineWith(mst_grade.reweightedGrade(40), greedy_grade.reweightedGrade(30))));
	}
	
	
}
