
import React, { useState } from 'react'


const Login = ({ onAuthed }) => {

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)

    async function handleLogin(e) {
        e.preventDefault()
        setError('')
        setLoading(true)

        try {
            const res = await api.post('/api/auth/login', {
                email,
                password
            })

            const token = res.data.accessToken
            onAuthed(token)

        } catch (err) {
            setError('Login failed. Check your email and password.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="min-h-screen bg-[#070b14] text-white flex items-center justify-center px-6 py-10 relative overflow-hidden">

            {/* Background Glow */}
            <div className="absolute -top-40 -left-40 w-96 h-96 bg-blue-600/20 rounded-full blur-3xl"></div>

            <div className="absolute -bottom-40 -right-40 w-96 h-96 bg-purple-600/20 rounded-full blur-3xl"></div>

            <div className="absolute top-1/2 left-1/2 w-72 h-72 bg-cyan-500/10 rounded-full blur-3xl"></div>


            {/* Main Container */}
            <div className="relative z-10 w-full max-w-6xl grid lg:grid-cols-2 gap-12 items-center">


                {/* LEFT SIDE */}
                <div className="hidden lg:block">

                    {/* Logo */}
                    <div className="flex items-center gap-3 mb-10">

                        <div className="w-11 h-11 rounded-xl bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center shadow-lg shadow-blue-500/20">

                            <svg
                                className="w-6 h-6"
                                fill="none"
                                stroke="currentColor"
                                viewBox="0 0 24 24"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth="2"
                                    d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l4.414 4.414A1 1 0 0118 8.414V19a2 2 0 01-2 2z"
                                />
                            </svg>

                        </div>

                        <div>
                            <h1 className="text-2xl font-bold tracking-tight">
                                Hire<span className="text-blue-400">Pilot</span>
                            </h1>

                            <p className="text-xs text-slate-500">
                                AI Career Assistant
                            </p>
                        </div>

                    </div>


                    {/* Heading */}

                    <div className="max-w-xl">

                        <div className="inline-flex items-center gap-2 px-3 py-1.5 rounded-full bg-blue-500/10 border border-blue-500/20 text-blue-400 text-sm mb-5">

                            <span className="w-2 h-2 bg-blue-400 rounded-full animate-pulse"></span>

                            AI-Powered Resume Analysis

                        </div>


                        <h2 className="text-5xl font-bold leading-tight">

                            Turn your resume into

                            <span className="block bg-gradient-to-r from-blue-400 via-cyan-400 to-purple-500 bg-clip-text text-transparent">
                                your career advantage.
                            </span>

                        </h2>


                        <p className="text-slate-400 text-lg mt-5 leading-relaxed max-w-lg">

                            Analyze your resume, discover your ATS score,
                            identify missing skills and find better job
                            opportunities with AI.

                        </p>

                    </div>


                    {/* Resume Analysis Preview */}

                    <div className="mt-10 relative">

                        <div className="bg-white/[0.04] backdrop-blur-xl border border-white/10 rounded-2xl p-5 max-w-lg shadow-2xl">

                            <div className="flex items-center justify-between mb-5">

                                <div className="flex items-center gap-3">

                                    <div className="w-10 h-10 rounded-lg bg-blue-500/10 flex items-center justify-center">

                                        📄

                                    </div>

                                    <div>

                                        <p className="font-medium">
                                            Resume Analysis
                                        </p>

                                        <p className="text-xs text-slate-500">
                                            AI analysis completed
                                        </p>

                                    </div>

                                </div>


                                <div className="text-green-400 text-sm font-medium">
                                    ✓ Analyzed
                                </div>

                            </div>


                            {/* Score */}

                            <div className="flex items-center gap-6">

                                <div className="relative w-24 h-24">

                                    <div className="w-24 h-24 rounded-full border-[6px] border-slate-700 flex items-center justify-center">

                                        <div className="text-center">

                                            <div className="text-2xl font-bold">
                                                87
                                            </div>

                                            <div className="text-[10px] text-slate-500">
                                                ATS SCORE
                                            </div>

                                        </div>

                                    </div>

                                </div>


                                <div className="flex-1 space-y-3">

                                    <div>

                                        <div className="flex justify-between text-xs mb-1">

                                            <span className="text-slate-400">
                                                Skills
                                            </span>

                                            <span className="text-green-400">
                                                92%
                                            </span>

                                        </div>

                                        <div className="h-1.5 bg-slate-700 rounded-full">

                                            <div className="h-1.5 w-[92%] bg-blue-500 rounded-full"></div>

                                        </div>

                                    </div>


                                    <div>

                                        <div className="flex justify-between text-xs mb-1">

                                            <span className="text-slate-400">
                                                Experience
                                            </span>

                                            <span className="text-green-400">
                                                86%
                                            </span>

                                        </div>

                                        <div className="h-1.5 bg-slate-700 rounded-full">

                                            <div className="h-1.5 w-[86%] bg-purple-500 rounded-full"></div>

                                        </div>

                                    </div>


                                    <div>

                                        <div className="flex justify-between text-xs mb-1">

                                            <span className="text-slate-400">
                                                Keywords
                                            </span>

                                            <span className="text-yellow-400">
                                                78%
                                            </span>

                                        </div>

                                        <div className="h-1.5 bg-slate-700 rounded-full">

                                            <div className="h-1.5 w-[78%] bg-cyan-400 rounded-full"></div>

                                        </div>

                                    </div>

                                </div>

                            </div>

                        </div>

                    </div>


                    {/* Features */}

                    <div className="flex gap-8 mt-8 text-sm text-slate-400">

                        <div className="flex items-center gap-2">
                            <span className="text-blue-400">✦</span>
                            AI Resume Analysis
                        </div>

                        <div className="flex items-center gap-2">
                            <span className="text-purple-400">✦</span>
                            ATS Optimization
                        </div>

                        <div className="flex items-center gap-2">
                            <span className="text-cyan-400">✦</span>
                            Job Matching
                        </div>

                    </div>

                </div>


                {/* RIGHT SIDE - LOGIN */}

                <div className="w-full max-w-md mx-auto">

                    {/* Mobile Logo */}

                    <div className="lg:hidden flex justify-center mb-8">

                        <div className="flex items-center gap-3">

                            <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-blue-500 to-purple-600 flex items-center justify-center">

                                📄

                            </div>

                            <h1 className="text-2xl font-bold">
                                Hire<span className="text-blue-400">Pilot</span>
                            </h1>

                        </div>

                    </div>


                    {/* Login Card */}

                    <div className="bg-white/[0.05] backdrop-blur-2xl border border-white/10 rounded-2xl p-8 shadow-2xl">

                        <div className="mb-8">

                            <h2 className="text-2xl font-bold">
                                Welcome back 👋
                            </h2>

                            <p className="text-slate-400 text-sm mt-2">
                                Sign in to continue analyzing your resume.
                            </p>

                        </div>


                        <form
                            onSubmit={handleLogin}
                            className="space-y-5"
                        >

                            {/* Email */}

                            <div>

                                <label className="block text-sm font-medium text-slate-300 mb-2">
                                    Email address
                                </label>

                                <div className="relative">

                                    <span className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500">
                                        ✉
                                    </span>

                                    <input
                                        type="email"
                                        placeholder="you@example.com"
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)}
                                        required
                                        className="w-full bg-slate-900/70 border border-slate-700 text-white rounded-xl pl-10 pr-4 py-3 text-sm outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-500/20 placeholder:text-slate-600"
                                    />

                                </div>

                            </div>


                            {/* Password */}

                            <div>

                                <div className="flex justify-between mb-2">

                                    <label className="text-sm font-medium text-slate-300">
                                        Password
                                    </label>

                                    <button
                                        type="button"
                                        className="text-xs text-blue-400 hover:text-blue-300"
                                    >
                                        Forgot password?
                                    </button>

                                </div>


                                <div className="relative">

                                    <span className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500">
                                        🔒
                                    </span>

                                    <input
                                        type="password"
                                        placeholder="Enter your password"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        required
                                        className="w-full bg-slate-900/70 border border-slate-700 text-white rounded-xl pl-10 pr-4 py-3 text-sm outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-500/20 placeholder:text-slate-600"
                                    />

                                </div>

                            </div>


                            {/* Error */}

                            {error && (

                                <div className="flex items-center gap-2 bg-red-500/10 border border-red-500/20 text-red-400 rounded-xl px-4 py-3 text-sm">

                                    <span>⚠</span>

                                    {error}

                                </div>

                            )}


                            {/* Login Button */}

                            <button
                                type="submit"
                                disabled={loading}
                                className="w-full bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-500 hover:to-purple-500 disabled:opacity-60 disabled:cursor-not-allowed text-white font-medium rounded-xl py-3 transition-all duration-200 shadow-lg shadow-blue-600/20"
                            >

                                {loading ? (
                                    <span className="flex items-center justify-center gap-2">

                                        <span className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin"></span>

                                        Analyzing...

                                    </span>
                                ) : (
                                    'Sign in to HirePilot →'
                                )}

                            </button>

                        </form>


                        {/* Divider */}

                        <div className="flex items-center gap-3 my-7">

                            <div className="h-px bg-slate-700 flex-1"></div>

                            <span className="text-xs text-slate-500">
                                AI-powered career platform
                            </span>

                            <div className="h-px bg-slate-700 flex-1"></div>

                        </div>


                        {/* Trust */}

                        <div className="grid grid-cols-3 gap-2 text-center">

                            <div className="bg-slate-900/50 rounded-lg p-3">

                                <div className="text-blue-400 font-semibold text-sm">
                                    AI
                                </div>

                                <div className="text-[10px] text-slate-500 mt-1">
                                    Powered
                                </div>

                            </div>

                            <div className="bg-slate-900/50 rounded-lg p-3">

                                <div className="text-purple-400 font-semibold text-sm">
                                    ATS
                                </div>

                                <div className="text-[10px] text-slate-500 mt-1">
                                    Optimized
                                </div>

                            </div>

                            <div className="bg-slate-900/50 rounded-lg p-3">

                                <div className="text-cyan-400 font-semibold text-sm">
                                    24/7
                                </div>

                                <div className="text-[10px] text-slate-500 mt-1">
                                    Available
                                </div>

                            </div>

                        </div>

                    </div>


                    <p className="text-center text-xs text-slate-600 mt-6">
                        © 2026 HirePilot · Your AI-powered career companion
                    </p>

                </div>

            </div>

        </div>
    )
}

export default Login
