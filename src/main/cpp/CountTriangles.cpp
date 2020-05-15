#include<iostream>

using namespace std;

main() {
    long a,b,c,d;
    cin >> a >> b >> c >> d;
    long long count = 0LL;
    for (long x = a; x <= b; x++) {
        for (long y = max(c - x + 1, b); y <= c; y++) {
            if (x + y > d) {
                count += ((long long) (d - c + 1)) * (c - y + 1);
                break;
            }
            count += (x + y - c);
       }
    }
    cout << count << "\n";
}
