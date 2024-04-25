import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/about',
            name: 'about',
            component: () => import('../views/AboutView.vue')
        }, {
            path: '/translation',
            name: 'translation',
            component: () => import('../views/TranslationView.vue')
        },
        {
            path: '/ai',
            name: 'ai',
            component: () => import('../views/AIView.vue')
        },
        {
            path: '/chat',
            name: 'chat',
            component: () => import('../views/ChatView.vue')
        },
        {
            path: '/my',
            name: 'my',
            component: () => import('../views/MyView.vue')
        }, {
            path: '/course',
            name: 'course',
            component: () => import('../views/CourseScheduleView.vue')
        },
        {
            path: '/ask',
            name: 'ask',
            component: () => import('../views/AskView.vue')
        }, {
            path: '/resource',
            name: 'resource',
            component: () => import('../views/ResourceView.vue')
        },
        {
            path: '/resource/:id',
            name: 'resource-detail',
            component: () => import('../views/ResoureDetailView.vue')
        },
        {
            path: '/ask/:id',
            name: 'ask-detail',
            component: () => import('../views/AskDetailView.vue')
        },
        {
            path: '/exam',
            name: 'exam',
            component: () => import('../views/ExamView.vue'),
            children: [{
                path: '',
                name: 'exam-home',
                component: () => import('../components/exam/ExamHome.vue')
            }, {
                path: 'answer/:id',
                name: 'exam-answer',
                component: () => import('../components/exam/ExamAnswerPage.vue')
            }, {
                path: 'analysis/:id',
                name: 'exam-analysis',
                component: () => import('../components/exam/ExamAnalysis.vue')
            }]
        }
        ,
        {
            path: '/manage',
            name: 'manage',
            component: () => import('../manage/ManageApp.vue'),
            children: [{
                path: 'courseTable',
                name: 'manage-courseTable',
                component: () => import('../manage/views/CourseTableMsgView.vue')
            },
                {
                    path: 'schedule',
                    name: 'manage-schedule',
                    component: () => import('../manage/pages/Schedule.vue')
                },
                {
                    path: 'attendance',
                    name: 'manage-attendance',
                    component: () => import('../manage/pages/Attendance.vue')
                },
                // 行政-----------------------------------------------------------------------------------------------------
                {
                    path: 'Classroom',
                    name: 'manage-Classroom',
                    component: () => import('../manage/pages/Classroom.vue')
                },
                {
                    path: 'Dormitory',
                    name: 'manage-Dormitory',
                    component: () => import('../manage/pages/Dormitory.vue')
                },
// 行政-----------------------------------------------------------------------------------------------------
                {
                    path: 'leaveRequest',
                    name: 'manage-leaveRequest',
                    component: () => import('../manage/pages/Leaverequest.vue')
                }, {
                    path: '',
                    name: 'manage-home',
                    component: () => import('../manage/views/HomeView.vue')
                }, {
                    path: 'special',
                    name: 'manage-special',
                    component: () => import('../manage/views/SpecialMsgView.vue')
                }, {
                    path: 'group',
                    name: 'manage-group',
                    component: () => import('../manage/views/GroupMsgView.vue')
                }, {
                    path: 'grades',
                    name: 'manage-grades',
                    component: () => import('../manage/views/GradesMsgView.vue')
                }, {
                    path: 'papers',
                    name: 'manage-papers',
                    component: () => import('../manage/views/PapersView.vue')
                }, {
                    path: 'class',
                    name: 'manage-class',
                    component: () => import('../manage/views/ClassMsgView.vue')
                }, {
                    path: 'course',
                    name: 'manage-course',
                    component: () => import('../manage/views/CourseMsgView.vue')
                }, {
                    path: 'student',
                    name: 'manage-student',
                    component: () => import('../manage/views/StudentMsgView.vue')
                }, {
                    path: 'teacher',
                    name: 'manage-teacher',
                    component: () => import('../manage/views/TeacherMsgView.vue')
                }, {
                    path: 'question',
                    name: 'manage-question',
                    component: () => import('../manage/views/QuestionMsgView.vue')
                }, {
                    path: 'statistics',
                    name: 'manage-statistics',
                    component: () => import('../manage/views/Statistics.vue')
                }, {
                    path: 'message',
                    name: 'manage-message',
                    component: () => import('../manage/views/MessageMsgView.vue')
                }, {
                    path: 'banner',
                    name: 'manage-banner',
                    component: () => import('../manage/views/BannerMsgView.vue')
                }, {
                    path: 'administrator',
                    name: 'manage-administrator',
                    component: () => import('../manage/views/AdministratorMsgView.vue')
                }, {
                    path: 'myself',
                    name: 'manage-myself',
                    component: () => import('../views/MyView.vue')
                }, {
                    path: 'onlineUsers',
                    name: 'manage-onlineUsers',
                    component: () => import('../manage/views/OnlineUsersView.vue')
                }, {
                    path: 'resource',
                    name: 'manage-resource',
                    component: () => import('../manage/views/SoftMsgView.vue')
                }]
        }
        ,
        {
            path: '/mobile',
            name: 'mobile',
            component: () => import('../mobile/MobileApp.vue'),
            children: [{
                path: '',
                name: 'mobile-home',
                component: () => import('../mobile/views/HomeView.vue')
            }, {
                path: 'questions',
                name: 'mobile-questions',
                component: () => import('../mobile/views/QuestionsView.vue')
            }, {
                path: 'message',
                name: 'mobile-message',
                component: () => import('../mobile/views/QuestionsView.vue')
            }, {
                path: 'my',
                name: 'mobile-my',
                component: () => import('../mobile/views/MyView.vue')
            }, {
                path: 'ai',
                name: 'mobile-ai',
                component: () => import('../mobile/views/AIView.vue')
            }]
        }

        ,
        {
            path: '/practice',
            name: 'practice',
            component: () => import('../views/PracticeView.vue'),
            children: [{
                path: '',
                name: 'practice-home',
                component: () => import('../components/practice/PracticeHome.vue'),
                children: [{
                    path: 'history',
                    name: 'practice-history',
                    component: () => import('../components/practice/PracticeHistory.vue')
                },
                    {
                        path: '',
                        name: 'practice-centre',
                        component: () => import('../components/practice/VueCentre.vue')
                    }]
            }, {
                path: 'history/:id',
                name: 'practice-history-detail',
                component: () => import('../components/practice/PracticeHistoryDetail.vue')
            }, {path: "Centre", name: 'Centre', component: () => import('../components/practice/PracticeHome.vue')},
                {
                    path: "topic",
                    name: 'practice-topic',
                    component: () => import('../components/practice/TopicList.vue')
                },
                {
                    path: "question",
                    name: 'practice-question',
                    component: () => import('../components/practice/PracticeAnswerPage.vue')
                },
                {path: "history", name: 'history', component: () => import('../components/practice/LookHistory.vue')},
                {
                    path: "vueHistory",
                    name: 'vueHistory',
                    component: () => import('../components/practice/PracticeHome.vue')
                },
                {path: "vueWrong", name: 'vueWrong', component: () => import('../components/practice/VueWrong.vue')},
                {path: "vueReport", name: 'vueReport', component: () => import('../components/practice/VueReport.vue')},
            ]
        },
        {
            path: '/game',
            name: 'game',
            component: () => import('../views/GameView.vue')
        }, {
            path: '/test',
            name: 'test',
            component: () => import('../views/TestView.vue')
        }
    ]
})

export default router
