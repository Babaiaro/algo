# Airtime

Fred has the altitude profile of a roller coaster: the height of the track measured once per second, in order. He gets one second of airtime at a point when the slope going into it is strictly greater than the slope coming out of it, meaning the track arcs downward there.

Given the measurements, count the seconds of airtime.

## Input

- The first line is `n`, the number of altitude measurements.
- The next `n` lines each hold one altitude in inches.

## Output

The total number of seconds of airtime.

## Approach

For every middle point `i` of three consecutive measurements, check

```
a[i] - a[i-1] > a[i+1] - a[i]
```

and count how many times it holds. The time between measurements is always one second, so each slope is just the difference in height. This runs in O(n) time.

## Example

Altitudes: `1 2 3 3 4 5 6 5 4 4 4 3 2`

Slopes between them: `+1 +1 0 +1 +1 +1 -1 -1 0 0 -1 -1`

| Points | Slope in | Slope out | What happens |
|---|---|---|---|
| 2, **3**, 3 | +1 | 0 | climb flattens out |
| 5, **6**, 5 | +1 | -1 | top of the hill |
| 4, **4**, 3 | 0 | -1 | flat section starts to drop |

Answer: **3**

## Run

```
python3 airtime.py < sample1.in
```
