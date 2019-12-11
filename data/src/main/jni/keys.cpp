#include <jni.h>
#include <regex>
#include <string>

using namespace std;

void replaceAll(string& s, const string& search, const string& replace ) {
    for( size_t pos = 0; ; pos += replace.length() ) {
        // Locate the substring to replace
        pos = s.find( search, pos );
        if( pos == string::npos ) break;
        // Replace by erasing and inserting
        s.erase( pos, search.length() );
        s.insert( pos, replace );
    }
}

string trim(const string& str, const string& whitespace = " \t\n\r\f\v")
{
    const auto strBegin = str.find_first_not_of(whitespace);
    if (strBegin == string::npos)
        return ""; // no content

    const auto strEnd = str.find_last_not_of(whitespace);
    const auto strRange = strEnd - strBegin + 1;

    return str.substr(strBegin, strRange);
}

string reduce(const string &str, const string &fill = " ", const string &whitespace = " \t\n\r\f\v") {
    // trim first
    auto result = trim(str, whitespace);

    // replace sub ranges
    auto beginSpace = result.find_first_of(whitespace);
    while (beginSpace != string::npos)
    {
        const auto endSpace = result.find_first_not_of(whitespace, beginSpace);
        const auto range = endSpace - beginSpace;

        result.replace(beginSpace, range, fill);

        const auto newStart = beginSpace + fill.length();
        beginSpace = result.find_first_of(whitespace, newStart);
    }

    return result;
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_ujujzk_ee_data_source_dic_local_model_Key_getKey(JNIEnv* env, jobject instance,
                                                          jstring squareBracketText) {

    string test = env->GetStringUTFChars(squareBracketText, nullptr);

    test = regex_replace(test, regex("\\\\\\\\(?:(?!\\\\\\\\).)+\\\\\\\\"), "");
    test = regex_replace(test, regex("\\[/?(\\*|!trs|'|trn|com)\\]"), "");
    replaceAll(test, "{", "");
    replaceAll(test, "}", "");
    test = regex_replace(test, regex("\\[(s|url)\\][^\\[]*\\[/(s|url)\\]"), "");

    test = regex_replace(test, regex("\\[/?lang[^\\[]*\\]"), "");

    test = regex_replace(test, regex("\\[c\\](\\[com\\])?"), "<font color=\'#2e7d32\'>");
    replaceAll(test, "[c]", "<font color=\'#2e7d32\'>");
    test = regex_replace(test, regex("(\\[/com\\])?\\[/c\\]"), "</font>");

    replaceAll(test, "[p]", "<font color=\'#2e7d32\'><i>");
    replaceAll(test, "[/p]", "</i></font>");

    test = regex_replace(test, regex("\\[(ex|c gray)\\]"), "<font color=\'#757575\'>");
    test = regex_replace(test, regex("\\[(c darkslategray)\\]"), "<font color=\'#2f4f4f\'>");
    test = regex_replace(test, regex("\\[/(ex|com)\\]"), "</font>");
    test = regex_replace(test, regex("\\[ref[^\\]]*\\]([^\\]]*)\\[/ref\\]"), "<a href='$1'>$1</a>");

    test = regex_replace(test, regex("\\[m[1-9]?\\]"), "");
    replaceAll(test, "[/m]", "");

    replaceAll(test, "[b]", "<b>");
    replaceAll(test, "[i]", "<i>");
    replaceAll(test, "[u]", "<u>");
    replaceAll(test, "[/b]", "</b>");
    replaceAll(test, "[/i]", "</i>");
    replaceAll(test, "[/u]", "</u>");

    replaceAll(test, "[sup]", "<sup>");
    replaceAll(test, "[sub]", "<sub>");
    replaceAll(test, "[/sup]", "</sup>");
    replaceAll(test, "[/sub]", "</sub>");

    replaceAll(test, "\\[", "[");
    replaceAll(test, "\\]", "]");
    test = regex_replace(test, regex("\\[\\[t\\][^\\[]*\\[/t\\]\\],?"), "");

    test = test.substr(test.find('\t') + 1);
    replaceAll(test, "\t", "");
    test = trim(test);
    replaceAll(test, "\n", "<br>");

    return env->NewStringUTF(test.c_str());
}




//extern "C"
//JNIEXPORT jstring JNICALL
//Java_com_ujujzk_ee_data_source_dic_local_model_Key_getNativeKey2(JNIEnv *env, jobject instance) {
//    return (*env) -> NewStringUTF(env, "TmF0aXZlNWVjcmV0UEBzc3cwcmQy");
//}