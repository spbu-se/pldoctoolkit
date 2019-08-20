import os

# useful dir paths
import jinja2

ROOT_DIR = os.path.dirname(os.path.realpath(__file__))
DIST_DIR = os.path.join(ROOT_DIR, 'dist')
JS_DIR = os.path.join(ROOT_DIR, 'js')
TEMPLATE_DIR = os.path.join(ROOT_DIR, 'templates')

template_env = jinja2.Environment(loader=jinja2.FileSystemLoader(os.path.join(ROOT_DIR, 'templates')))
