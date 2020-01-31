#!/usr/bin/env python3
# python script to generate a Sim class and SimJNI class a SimJNI class with native methods
# replace the native methods to call our Sim* static class
# ./gensimwrapper.py replace-native-methods -src-file ../java/frc/robot/simulator/hal/SimHALJNI.java -sim-name SimHAL -print
# generate the Sim* static class
# ./gensimwrapper.py gen-sim-class -src-file ../java/frc/robot/simulator/hal/SimHALJNI.java -sim-name SimHALJNI -print

# for vendor versions
# ./gensimwrapper.py replace-native-methods -src-file ../../../../simulator/src/main/java/frc/robot/simulator/hal/SimSolenoidJNI.java -sim-name SimSolenoid -print
# generate the Sim* static class
# ./gensimwrapper.py gen-sim-class -src-file ../../../../simulator/src/main/java/frc/robot/simulator/hal/SimSolenoidJNI.java -sim-name SimSolenoid -print

import argparse
import os
import re
import sys
import logging


log = logging.getLogger(__name__)

sim_name = ''


def replace_native_method(m):
    (method_ret_type, method_name, method_args_string) = m.groups()
    if 'throws' in m.group(0):
        log.debug("Ignoring method with throws in it")
        return m.group(0)
    if method_args_string.strip() != '':
        method_args = [a.split()
                       for a in method_args_string.replace("\n", "").split(",")]
    else:
        method_args = []

    log.debug(f"Found method: {method_ret_type} {method_name} {method_args}")

    method_header = f"    public static {method_ret_type} {method_name}({method_args_string}) " + "{\n"

    sim_method = '        '
    if method_ret_type != 'void':
        sim_method = '        return '
    sim_method += f"{sim_name}.{method_name}("
    if len(method_args) > 0:
        sim_method += ", ".join([a for t, a in method_args])
    sim_method += ");"
    sim_method += "\n    }"
    return method_header + sim_method


def replace_native_methods(argv):
    parser = argparse.ArgumentParser()
    parser.add_argument('-src-file', required=True)
    parser.add_argument('-sim-name', required=True)
    parser.add_argument('-out', action='store_true')
    parser.add_argument('-print', action='store_true')

    args, extras = parser.parse_known_args(argv)

    if not args.src_file or not os.path.exists(args.src_file):
        print(f"Can't find source file: {args.src_file}")
        sys.exit(-1)

    global sim_name
    sim_name = args.sim_name

    method_pattern = r'public static native ([A-za-z_0-9\[\]]+) (\w+)\((.*?)\);'
    with open(args.src_file, 'r') as f:
        content = f.read()
        updated_content = re.sub(method_pattern, replace_native_method,
                                 content, flags=(re.MULTILINE | re.DOTALL))

        if args.print:
            print(updated_content)


def gen_sim_class(argv):
    parser = argparse.ArgumentParser()
    parser.add_argument('-src-file', required=True)
    parser.add_argument('-sim-name', required=True)
    parser.add_argument('-out', action='store_true')
    parser.add_argument('-print', action='store_true')

    args, extras = parser.parse_known_args(argv)

    if not args.src_file or not os.path.exists(args.src_file):
        print(f"Can't find source file: {args.src_file}")
        sys.exit(-1)

    global sim_name
    sim_name = args.sim_name

    updated_content = f"""
package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class {sim_name} {{
    private static final Logger log = LoggerFactory.getLogger({sim_name}.class);

    """

    method_pattern = r'public static ([A-za-z_0-9\[\]]+) (\w+)\((.*?)\) {'
    with open(args.src_file, 'r') as f:
        content = f.read()
        for m in re.findall(method_pattern, content, flags=re.DOTALL):
            log.debug("match: " + str(m))
            (method_ret_type, method_name, method_args_string) = m
            new_method = f"""
    public static {method_ret_type} {method_name}({method_args_string}) {{
        log.warn("{method_name} not implemented yet.");
            """
            if method_ret_type == 'boolean':
                new_method = new_method + """
        return false;
    }
                """
            elif method_ret_type in ['int', 'long', 'double', 'float', 'short']:
                new_method = new_method + """
        return 0;
    }
                """
            elif method_ret_type == 'void':
                new_method = new_method + """
    }
                """
            else:
                new_method = new_method + """
        return null;
    }
                """
            updated_content = updated_content + new_method

        updated_content = updated_content + """
}
"""
        if args.print:
            print(updated_content)


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-debug', action='store_true', default=False)

    args, extras = parser.parse_known_args()

    logging.basicConfig(
        format='[%(levelname)s][%(asctime)s][%(name)s] %(message)s')
    if args.debug:
        log.setLevel(logging.DEBUG)
        log.debug("Set level to debug")

    if len(extras) == 0:
        print("Must specify an action like replace-native-methods or gen-sim-class")
        sys.exit(-1)

    action = extras[0]
    if action == "replace-native-methods":
        replace_native_methods(extras)
    elif action == "gen-sim-class":
        gen_sim_class(extras)
    else:
        print(f"Unknown action {action}")
