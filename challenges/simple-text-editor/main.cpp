#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include <stack>

using namespace std;

int main() {
    int n;
    int o;
    string text;
    int index;
    
    stack<string> texts;
    texts.push("");    
    
    cin >> n;    
    
    for(int i = 0; i < n; i++) {        
        cin >> o;
        switch(o) {
            case 1:
                cin >> text;
                texts.push(texts.top() + text);
                break;
            case 2: 
                cin >> index;  
                texts.push(texts.top().substr(0, texts.top().length() - index));                
                break;
            case 3: 
                cin >> index;            
                cout << texts.top()[index - 1] << endl;
                break;
            case 4: 
                texts.pop();  
                break;                                             
        }
    }
 
    return 0;
}
