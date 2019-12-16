from . import ReportHook

import re
_dogword = re.compile(r"(@(param|retrun|exception|deprecated|since|throw|version|author|see|since|serial))")

class JavaDocHook(ReportHook):
    @staticmethod
    def transform_nearduplicate_html(html: str)-> str:
        return _dogword.sub(r"<br/>\1", html).lstrip()
