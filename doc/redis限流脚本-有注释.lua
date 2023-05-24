-- 持久化和主从复制
redis.replicate_commands();
-- 获取传入参数
local singleKey = KEYS[1]
local singleUserKey = KEYS[2]
local wholeKey = KEYS[3]
local wholeLimiterKey = KEYS[4]
-- 获取当前时间，10位时间戳
local currentTime = redis.call('TIME')[1]
-- 获取当前时间，16位，包含毫秒
local currentTimeMiss = redis.call('TIME')[1]..redis.call('TIME')[2]
-- 判断当前接口时否有单用户限制
if redis.call('EXISTS',singleKey)==1 then
	-- 获取当前接口限制配置
	local maxSize = tonumber(redis.call('hget',singleKey,'max'))
	local outTime = tonumber(redis.call('hget',singleKey,'outTime'))
	local currentLen = redis.call('ZCARD',singleUserKey)
	-- 判断是否达到最大次数 ZCARD 如果没有key 则返回 0 有key 则返回key的 长度
	if  currentLen < maxSize then
		-- 没有达到最大次数 直接通过
		redis.call('ZADD',singleUserKey,currentTime,currentTimeMiss)
		redis.call('expire',singleUserKey,outTime)
	else
		-- 达到最大次数 计算已无效的范围 即 0 到 minTime 中的访问 不再计算次数了
		local minTime = currentTime - outTime
		-- 统计有效次数
		local effectiveNum = redis.call('ZCOUNT',singleUserKey,minTime,currentTime)
		-- 判断有效次数小于最大次数 并且 移除无效次数大于0
		if  effectiveNum < maxSize and redis.call('ZREMRANGEBYSCORE',singleUserKey,0,minTime) > 0 then
			-- 移除成功 通过
			redis.call('ZADD',singleUserKey,currentTime,currentTimeMiss)
			redis.call('expire',singleUserKey,outTime)
		else
			-- 失败 不通过
			return -1
		end
	end
end
-- 判断当前接口是否有全体用户限制
if redis.call('EXISTS',wholeKey)==1 then
	-- 获取全体用户限制配置
	local maxSizeWhole = tonumber(redis.call('hget',wholeKey,'max'))
	local outTimeWhole = tonumber(redis.call('hget',wholeKey,'outTime'))
	local currentLenWhole = redis.call('ZCARD',wholeLimiterKey)
	-- 判断是否达到最大次数 ZCARD 如果没有key 则返回 0 有key 则返回key的 长度
	if currentLenWhole < maxSizeWhole then
		-- 没有达到最大次数 直接通过
		redis.call('ZADD',wholeLimiterKey,currentTime,currentTimeMiss)
		redis.call('expire',wholeLimiterKey,outTimeWhole)
	else
		-- 达到最大次数 计算已无效的范围 即 0 到 minTimeWhole 中的访问 不再计算次数了
		local minTimeWhole = currentTime - outTimeWhole
		-- 统计有效次数
		local effectiveNumWhole = redis.call('ZCOUNT',wholeLimiterKey,minTimeWhole,currentTime)
		-- 判断有效次数小于最大次数 并且 移除无效次数大于0
		if  effectiveNumWhole < maxSizeWhole and redis.call('ZREMRANGEBYSCORE',wholeLimiterKey,0,minTimeWhole) > 0 then
			-- 移除成功 通过
			redis.call('ZADD',wholeLimiterKey,currentTime,currentTimeMiss)
			redis.call('expire',wholeLimiterKey,outTimeWhole)
		else
			-- 失败 不通过
			return -2
		end
	end
end
-- 正常结束 返回 0
return 0
