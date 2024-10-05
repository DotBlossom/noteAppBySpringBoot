## RDBS를 이용해서 Notion을 만듦
    1. MongoDB와 같은 Json 깊이 접근이 지원이 되지 않아, 노트의 순서를 유지하고 각 줄 CRUD 위해 generateKey 개념 도입
   
    2. note -> lines(blocks) -> image || relational lines(block) 
       note -> lines || index table -> image 
       note -> lines || index table 
       
       여러 fetching case의 쿼리 최적화를 위해, entity-graph + <T> JPA QueryMethod 상속 + DTO projection을 도입
  
    3. relational Block을 도입함

## relational block?
    다른 노트의 문장을 가져와서 링크를 거는 것
    
    용도 : 내가 수학 공부를 하는데, 적분의 평균값 정리를 작성하면서 저번에 작성한 미분 쪽 평균값 정리 내용을 Linker
    
    기대효과 : 지속적으로 연계된 내용을 쉽게 추적하고 볼 수 있음
    
    추후 개발 : 연결된 block들을 실제 Panning 기능이 있는 Graph-node 형태로 지식흐름을 쉽게 시각적 overview 할수 있음
  
## index table (목차)?
    노션의 그 목차를 순서에 맞게 생성해주는 알고리즘
  
    추후 개발 : index table의 data를 하나의 contexts로 tokenizer하여, 사용자가 추상적인 검색어를 치더라도, 내 노트 안에서 
              DL 로직을 이용하여 내용을 찾게 해주는 검색 딥러닝 개발 하고 싶었음 . 
             
    자세한 내용은 생략.
