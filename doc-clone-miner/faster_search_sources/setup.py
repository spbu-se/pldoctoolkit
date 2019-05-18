from distutils.core import setup
from Cython.Build import cythonize

setup(
  name = 'Near duplicate search',
  ext_modules = cythonize("faster_pattern_near_duplicate_search.pyx"),
)
# python setup.py build_ext --inplace
