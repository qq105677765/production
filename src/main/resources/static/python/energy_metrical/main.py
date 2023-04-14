
import datetime
import json
import sys
import pandas as pd
import requests
import xmltodict

#############################################
# input:    介质 车间 开始时间 结束时间
# output:   数据(list)
#############################################

# 请求地址
url = "http://10.236.11.44:8099/ieemweb/WebService/EEMWebService.asmx/GetMetricalCompareDatas"
# 载荷
payload = {
    "dataID": 4000004,
    "nodeID": 82,
    "nodeType": 536940544,
    "startTime": "2023-03-01 00:00:00",
    "endTime": "2023-04-01 00:00:00",
    "isMonthly": "false",
    "timeInterval": 8
}
# mapping work_shop, energy_metrical to dataID,nodeID,nodeType
mapping = {
    "自来水": {
        "冲压车间": {
            "dataID": 10000001,
            "nodeID": 31,
            "nodeType": 536940544,
        },
        "焊装车间": {
            "dataID": 10000001,
            "nodeID": 32,
            "nodeType": 536940544,
        },
        "涂装车间": {
            "dataID": 10000001,
            "nodeID": 33,
            "nodeType": 536940544,
        },
        "总装车间": {
            "dataID": 10000001,
            "nodeID": 34,
            "nodeType": 536940544,
        },
        "技术中心": {
            "dataID": 10000001,
            "nodeID": 35,
            "nodeType": 536940544,
        },
        "联合站房": {
            "dataID": 10000001,
            "nodeID": 36,
            "nodeType": 536940544,
        },
        "安监中心": {
            "dataID": 10000001,
            "nodeID": 37,
            "nodeType": 536940544,
        },
        "分拣中心": {
            "dataID": 10000001,
            "nodeID": 38,
            "nodeType": 536940544,
        },
        "培训中心": {
            "dataID": 10000001,
            "nodeID": 41,
            "nodeType": 536940544,
        },
        "污水处理站": {
            "dataID": 10000001,
            "nodeID": 42,
            "nodeType": 536940544,
        },
        "管控食堂": {
            "dataID": 10000001,
            "nodeID": 43,
            "nodeType": 536940544,
        },
        "506号门": {
            "dataID": 10000001,
            "nodeID": 44,
            "nodeType": 536940544,
        },
        "新产品库": {
            "dataID": 10000001,
            "nodeID": 45,
            "nodeType": 536940544,
        },
        "报废资产库": {
            "dataID": 10000001,
            "nodeID": 46,
            "nodeType": 536940544,
        },
        "油库": {
            "dataID": 10000001,
            "nodeID": 47,
            "nodeType": 536940544,
        },
        "新能源车间": {
            "dataID": 10000001,
            "nodeID": 48,
            "nodeType": 536940544,
        },
    },
    "热-高温水": {
        "涂装车间": {
            "dataID": 10000001,
            "nodeID": 49,
            "nodeType": 536940544,
        },
        "总装车间": {
            "dataID": 10000001,
            "nodeID": 50,
            "nodeType": 536940544,
        },
        "联合站房": {
            "dataID": 10000001,
            "nodeID": 51,
            "nodeType": 536940544,
        },
    },
    "热-采暖水": {
        "冲压车间": {
            "dataID": 10000001,
            "nodeID": 52,
            "nodeType": 536940544,
        },
        "焊装车间": {
            "dataID": 10000001,
            "nodeID": 53,
            "nodeType": 536940544,
        },
        "涂装车间": {
            "dataID": 10000001,
            "nodeID": 54,
            "nodeType": 536940544,
        },
        "总装车间": {
            "dataID": 10000001,
            "nodeID": 55,
            "nodeType": 536940544,
        },
        "技术中心": {
            "dataID": 10000001,
            "nodeID": 56,
            "nodeType": 536940544,
        },
        "安监中心": {
            "dataID": 10000001,
            "nodeID": 57,
            "nodeType": 536940544,
        },
        "分拣中心": {
            "dataID": 10000001,
            "nodeID": 58,
            "nodeType": 536940544,
        },
        "培训中心": {
            "dataID": 10000001,
            "nodeID": 61,
            "nodeType": 536940544,
        },
        "污水处理站": {
            "dataID": 10000001,
            "nodeID": 62,
            "nodeType": 536940544,
        },
        "管控食堂": {
            "dataID": 10000001,
            "nodeID": 63,
            "nodeType": 536940544,
        },
        "新产品库": {
            "dataID": 10000001,
            "nodeID": 64,
            "nodeType": 536940544,
        },
        "车身编组站": {
            "dataID": 10000001,
            "nodeID": 65,
            "nodeType": 536940544,
        },
        "新能源车间": {
            "dataID": 10000001,
            "nodeID": 66,
            "nodeType": 536940544,
        },
    },
    "天燃气": {
        "总装车间": {
            "dataID": 10000001,
            "nodeID": 67,
            "nodeType": 536940544,
        },
        "安监中心": {
            "dataID": 10000001,
            "nodeID": 68,
            "nodeType": 536940544,
        },
        "管控食堂": {
            "dataID": 10000001,
            "nodeID": 69,
            "nodeType": 536940544,
        },
        "涂装车间": {
            "dataID": 10000001,
            "nodeID": 105,
            "nodeType": 536940544,
        },
    },
    "压缩空气6bar": {
        "冲压车间": {
            "dataID": 10000001,
            "nodeID": 70,
            "nodeType": 536940544,
        },
        "焊装车间": {
            "dataID": 10000001,
            "nodeID": 71,
            "nodeType": 536940544,
        },
        "涂装车间": {
            "dataID": 10000001,
            "nodeID": 72,
            "nodeType": 536940544,
        },
        "总装车间": {
            "dataID": 10000001,
            "nodeID": 73,
            "nodeType": 536940544,
        },
        "污水处理站": {
            "dataID": 10000001,
            "nodeID": 74,
            "nodeType": 536940544,
        },
        "油库": {
            "dataID": 10000001,
            "nodeID": 76,
            "nodeType": 536940544,
        },
        "新能源车间": {
            "dataID": 10000001,
            "nodeID": 77,
            "nodeType": 536940544,
        },
    },
    "压缩空气12bar": {
        "焊装车间": {
            "dataID": 10000001,
            "nodeID": 78,
            "nodeType": 536940544,
        },
        "技术中心": {
            "dataID": 10000001,
            "nodeID": 79,
            "nodeType": 536940544,
        },
    },
    "电": {
        "冲压车间": {
            "dataID": 4000004,
            "nodeID": 81,
            "nodeType": 536940544,
        },
        "焊装车间": {
            "dataID": 4000004,
            "nodeID": 82,
            "nodeType": 536940544,
        },
        "涂装车间": {
            "dataID": 4000004,
            "nodeID": 83,
            "nodeType": 536940544,
        },
        "总装车间": {
            "dataID": 4000004,
            "nodeID": 84,
            "nodeType": 536940544,
        },
        "技术中心": {
            "dataID": 4000004,
            "nodeID": 85,
            "nodeType": 536940544,
        },
        "联合站房": {
            "dataID": 4000004,
            "nodeID": 86,
            "nodeType": 536940544,
        },
        "安监中心": {
            "dataID": 4000004,
            "nodeID": 87,
            "nodeType": 536940544,
        },
        "分拣中心": {
            "dataID": 4000004,
            "nodeID": 88,
            "nodeType": 536940544,
        },
        "培训中心": {
            "dataID": 4000004,
            "nodeID": 89,
            "nodeType": 536940544,
        },
        "污水处理站": {
            "dataID": 4000004,
            "nodeID": 90,
            "nodeType": 536940544,
        },
        "管控食堂": {
            "dataID": 4000004,
            "nodeID": 91,
            "nodeType": 536940544,
        },
        "506号门": {
            "dataID": 4000004,
            "nodeID": 92,
            "nodeType": 536940544,
        },
        "新产品库": {
            "dataID": 4000004,
            "nodeID": 93,
            "nodeType": 536940544,
        },
        "报废资产库": {
            "dataID": 4000004,
            "nodeID": 94,
            "nodeType": 536940544,
        },
        "油库": {
            "dataID": 4000004,
            "nodeID": 95,
            "nodeType": 536940544,
        },
        "新能源车间": {
            "dataID": 4000004,
            "nodeID": 96,
            "nodeType": 536940544,
        },
        "车身编组站": {
            "dataID": 4000004,
            "nodeID": 97,
            "nodeType": 536940544,
        },
    }
}


def run(energy_metrical, work_shop, start_time, end_time):
    result = []

    # 设置载荷
    payload["startTime"] = convertTimeFormat(start_time)
    payload["endTime"] = convertTimeFormat(end_time)
    convertToRequestData(energy_metrical, work_shop)

    # 发起web请求
    r = requests.post(url, data=payload)
    # convert xml to json
    data_dict = xmltodict.parse(r.text)
    # pick-up core data
    result = data_dict['WebGeneralResultOfMetricalFixPeriodData']['ResultList']['MetricalFixPeriodData']

    return result

# convert timestamp to format date
def convertTimeFormat(timestamp):
    d = datetime.datetime.fromtimestamp(float(timestamp))
    str = d.strftime('%Y-%m-%d %H:%M:%S')
    return str


# 介质、车间转换为请求载荷
def convertToRequestData(energy_metrical, work_shop):
    # 获取对应信息
    correspond_mapping = mapping[energy_metrical][work_shop]

    # 更新对应key
    for k in correspond_mapping.keys():
        payload[k] = correspond_mapping[k]


if __name__ == "__main__":
    # get input argument
    param_list = sys.argv
    energy_metrical, work_shop, start_time, end_time = param_list[
        1], param_list[2], param_list[3], param_list[4]

    data = run(energy_metrical, work_shop, start_time, end_time)
    # 测试用例:
    # data = run("自来水", "冲压车间", 1677600000, 1677686400)

    print(json.dumps(data))
