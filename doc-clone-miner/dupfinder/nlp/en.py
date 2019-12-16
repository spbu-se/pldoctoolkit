import lemminflect
import re
import sys

_nonalpha = re.compile(r'\W+')

def normalize(w: 'str')-> 'str':
    try:
        wa = _nonalpha.sub('', w)
        if 0 == len(wa):
            return w

        wl = lemminflect.getLemma(wa.lower(), None)

        return wl[0]
    except Exception as e:
        print(f"When normalizing: {w}, got error {e}", file=sys.stderr)
        return w
