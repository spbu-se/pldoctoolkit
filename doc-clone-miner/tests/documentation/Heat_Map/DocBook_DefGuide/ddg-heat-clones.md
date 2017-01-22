# DDG Heat clones

1. 5f903e11-45e9-49d4-9b17-8a05b6e78236
    1.1.

        The result will be something like this: <?xml version='1.0'?>
        <!DOCTYPE book PUBLIC &quot;-//OASIS//DTD DocBook XML V4.1.2//EN&quot;
                      &quot;http://www.oasis-open.org/docbook/xml/4.1.2/docbookx.dtd&quot;>
        <chapter><title>Chapter Title</title>
        <indexterm id=&quot;idx-bor&quot;><primary>Something</primary></indexterm>
        <para>
        Paragraph test.
        </para>
        <indexterm startref=&quot;idx-bor&quot;/>
        </chapter>
    
    1.2.

        The result will be something like this: <?xml version='1.0'?>
        <!DOCTYPE book PUBLIC &quot;-//OASIS//DTD DocBook XML V4.1.2//EN&quot;
                      &quot;http://www.oasis-open.org/docbook/xml/4.1.2/docbookx.dtd&quot;>
        <chapter><title>Chapter Title</title>
        <para>
        Write to us at:
        <address format=&quot;linespecific&quot;>
        90 Sherman Street
        Cambridge, MA 02140
        </address>
        </para>
        </chapter>

    1.3.

        The result will be something like this: <?xml version='1.0'?>
        <!DOCTYPE book PUBLIC &quot;-//OASIS//DTD DocBook XML V4.1.2//EN&quot;
                          &quot;http://www.oasis-open.org/docbook/xml/4.1.2/docbookx.dtd&quot;>
        <chapter><title>Chapter Title</title>
        <para>
        The following code is totally out of context:
        <programlisting>
        <![CDATA[
        int main () {
        ..
        }
        ]]>
        </programlisting>
        </chapter>

    1.4.

        The result will be something like this: <?xml version='1.0'?>
        <!DOCTYPE book PUBLIC "-//OASIS//DTD DocBook XML V4.1.2//EN"
                          "http://www.oasis-open.org/docbook/xml/4.1.2/docbookx.dtd">
        <book><title>Book Title</title>
        <chapter><title>Chapter Title</title>
        <para>
        Paragraph test.
        </para>
        <para>
        A second paragraph.
        </para>
        </chapter>
        </book>

    1.5.

        The result will be something like this: <?xml version='1.0'?>
        <!DOCTYPE book PUBLIC "-//OASIS//DTD DocBook XML V4.1.2//EN"
                  "http://www.oasis-open.org/docbook/xml/4.1.2/docbookx.dtd">
        <chapter id="chap1"><title>Chapter Title</title>
        <para>
        This <emphasis>paragraph</emphasis> is important.
        </para>
        </chapter>

    1.6.

        The result will be something like this: <?xml version='1.0'?>
        <!DOCTYPE book PUBLIC "-//OASIS//DTD DocBook XML V4.1.2//EN"
                  "http://www.oasis-open.org/docbook/xml/4.1.2/docbookx.dtd">
        <chapter><title>Chapter Title</title>
        <para>
        The following code is totally out of context:
        <programlisting>
        <![CDATA[
        if (x < 3) {
          y = 3;
        }
        ]]>
        </programlisting>
        </chapter>

    1.7.

        The result will be something like this: <?xml version='1.0'?>
        <!DOCTYPE book PUBLIC "-//OASIS//DTD DocBook XML V4.1.2//EN"
                  "http://www.oasis-open.org/docbook/xml/4.1.2/docbookx.dtd" [
        <!ENTITY trade "&#x2122;">
        <chapter><title>Chapter Title</title>
        <para>
        This book was published by O'Reilly&trade;.
        </para>
        </chapter> Case-Sensitivity <!DocType Book PUBLIC "-//OASIS//DTD DocBook V3.1//EN">
         <book><title>Book Title</title>
        <chapter><title>Chapter Title </Title>
        <para>
        Paragraph test.
        </para>
         <PARA>
        A second paragraph.
        </PARA>
        </chapter>
        </book>

