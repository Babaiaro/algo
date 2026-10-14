n = int(input())
a = [int(input()) for _ in range(n)]
print(sum(a[i] - a[i-1] > a[i+1] - a[i] for i in range(1, n - 1)))
