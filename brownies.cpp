// Brownie laser cuts: minimum number of straight cuts across a convex polygon
// so the pieces' total perimeter grows by at least L.
//
// Each cut adds twice its chord length, the longest chord is the diameter D,
// and only cuts along an interior diameter reach exactly D; every other cut is
// strictly shorter but can come arbitrarily close.
//
// Input: n L, then n integer points in polygon order.
#include <bits/stdc++.h>
using namespace std;

typedef long long ll;
typedef __int128 lll;

struct P {
    ll x, y;
};

lll cross(const P& o, const P& a, const P& b) {
    return (lll)(a.x - o.x) * (b.y - o.y) - (lll)(a.y - o.y) * (b.x - o.x);
}

lll dist2(const P& a, const P& b) {
    lll dx = a.x - b.x, dy = a.y - b.y;
    return dx * dx + dy * dy;
}

lll abs128(lll v) { return v < 0 ? -v : v; }

// Drops vertices that sit in the middle of a straight edge.
vector<P> removeCollinear(const vector<P>& pts) {
    vector<P> h;
    for (const P& p : pts) {
        while (h.size() >= 2 && cross(h[h.size() - 2], h.back(), p) == 0) h.pop_back();
        h.push_back(p);
    }
    // Also check the wrap-around where the polygon closes.
    bool changed = true;
    while (changed && h.size() >= 3) {
        changed = false;
        int n = h.size();
        if (cross(h[n - 2], h[n - 1], h[0]) == 0) { h.pop_back(); changed = true; continue; }
        if (cross(h[n - 1], h[0], h[1]) == 0) { h.erase(h.begin()); changed = true; }
    }
    return h;
}

int main() {
    int n;
    ll L;
    if (!(cin >> n >> L)) return 0;
    vector<P> pts(n);
    for (auto& p : pts) cin >> p.x >> p.y;

    if (L <= 0) {
        cout << 0 << "\n";
        return 0;
    }

    vector<P> h = removeCollinear(pts);
    n = h.size();

    // Rotating calipers: every diameter is an antipodal pair, so checking
    // all antipodal pairs finds D and every pair of vertices at distance D.
    lll best = 0;
    set<pair<int, int>> diameters;
    auto consider = [&](int a, int b) {
        if (a == b) return;
        lll d = dist2(h[a], h[b]);
        if (d > best) {
            best = d;
            diameters.clear();
        }
        if (d == best) diameters.insert({min(a, b), max(a, b)});
    };
    int j = 1;
    for (int i = 0; i < n; i++) {
        int ni = (i + 1) % n;
        while (abs128(cross(h[i], h[ni], h[(j + 1) % n])) > abs128(cross(h[i], h[ni], h[j]))) j = (j + 1) % n;
        consider(i, j);
        consider(ni, j);
        // A parallel opposite edge gives a second antipodal vertex.
        if (abs128(cross(h[i], h[ni], h[(j + 1) % n])) == abs128(cross(h[i], h[ni], h[j]))) {
            consider(i, (j + 1) % n);
            consider(ni, (j + 1) % n);
        }
    }

    // A diameter between neighbouring vertices lies along an edge and cuts
    // nothing off, so only the others can be used at full length.
    ll usable = 0;
    for (auto [a, b] : diameters)
        if (b - a != 1 && !(a == 0 && b == n - 1)) usable++;

    // Smallest k with 2kD >= L, i.e. 4 k^2 D^2 >= L^2, in exact arithmetic.
    lll target = (lll)L * L;
    auto enough = [&](ll k) { return (lll)4 * k * k * best >= target; };
    ll k = max<ll>(1, (ll)ceill((long double)L / (2 * sqrtl((long double)best))));
    while (k > 1 && enough(k - 1)) k--;
    while (!enough(k)) k++;

    // Exactly equal means every cut must be a full diameter.
    bool exact = (lll)4 * k * k * best == target;
    if (exact && k > usable) k++;

    cout << k << "\n";
    return 0;
}
